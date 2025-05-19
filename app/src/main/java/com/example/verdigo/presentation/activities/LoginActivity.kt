package com.example.verdigo.presentation.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.clase_nativas.utils.ToastAlert
import com.example.verdigo.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInApi
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.gson.Gson
import kotlin.math.log

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var loginButton: Button
    private lateinit var loginButtonGoogle: Button

    private lateinit var forgotPasswordTextView: TextView
    private lateinit var signUpButton: Button

    private lateinit var mGoogleSignInClient:GoogleSignInClient
    private val RC_SIGN_IN = 123
    private val TAG = "GoogleSignIn"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        initializeViews()
        setListeners()
    }

    private fun initializeViews() {
        //Inicializar las variables
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE)
        editTextEmail = findViewById(R.id.emailEditText)
        editTextPassword = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        loginButtonGoogle = findViewById(R.id.btnGoogleLogin)
        forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView)
        signUpButton = findViewById(R.id.signUpButton)
    }

    private fun setListeners() {
        loginButton.setOnClickListener {
            //Validamos el inicio de sesión
            if(validateLogin()){
                //redireccionamiento
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        loginButtonGoogle.setOnClickListener {
            signIn()
        }

        // Redirigimos al formulario de recuperar contraseña
        forgotPasswordTextView.setOnClickListener {
            val intent = Intent(this, PasswordRecoveryActivity::class.java)
            startActivity(intent)
        }

        //Redirigimos al formulario de registro
        signUpButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signIn(){
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>){
        try{
            val account = completedTask.getResult(ApiException::class.java)

            val gson = Gson()
            val json = gson.toJson(account)

            Log.d("LoginActivity account", "Account JSON: $json")
            Log.d("GoogleSignIn", "ID: ${account.id}")
            Log.d("GoogleSignIn", "Email: ${account.email}")
            Log.d("GoogleSignIn", "Display Name: ${account.displayName}")
            Log.d("GoogleSignIn", "Given Name (Nombre): ${account.givenName}")
            Log.d("GoogleSignIn", "Family Name (Apellido): ${account.familyName}")
            Log.d("GoogleSignIn", "Photo URL: ${account.photoUrl}")
            Log.d("GoogleSignIn", "ID Token: ${account.idToken}")

            //Inicio de sesión exitoso
            ToastAlert.show(this,"Bienvenid@ ${account.givenName} ${account.familyName}")

            //Guardamos los datos personales
            val editor = sharedPreferences.edit()
            editor.putString("name", account.givenName.toString().trim())
            editor.putString("lastNames", account.familyName.toString().trim())
            editor.putString("email", account.email.toString().trim())
            editor.apply()

            intent = Intent(this, MainActivity::class.java)
            intent.putExtra("USER_EMAIL", account.email)
            intent.putExtra("USER_NAME", account.displayName)
            startActivity(intent)

        }catch (e:ApiException){

            val mensaje = when(e.statusCode){
                10 -> "Error de configuración. Verifica la huella SHA-1"
                12500 -> "Error de conexión. Verifica tu conexión a internet"
                12501 -> "Inicio de sesión cancelado por el usuario"
                else -> "Error al iniciar sesión (código ${e.statusCode})"
            }
        }
    }

    fun validateLogin():Boolean{
        val email = editTextEmail.text.toString().trim()
        val password = editTextPassword.text.toString().trim()
        var isValid = true

        //Datos registrados
        val registeredEmail = sharedPreferences.getString("email", "")
        val registeredPassword = sharedPreferences.getString("password", "")

        // Validación de email
        if (email.isEmpty()) {
            editTextEmail.error = "El campo Email es requerido"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.error = "Ingresa un email válido"
            isValid = false
        }

        //Validación contraseña
        if (password.isEmpty()) {
            editTextPassword.error = "El campo Contraseña es requerido"
            isValid = false
        }

        if(!isValid) {
            ToastAlert.show(
                this,
                "Hay errores en el formulario, por favor verifique cada uno para poder iniciar sesión."
            )
            return false
        }

        //Si los campos son validos, verificamos que sean iguales a los registrados
        if(!email.equals(registeredEmail)){
            ToastAlert.show(
                this,
                "El correo ingresado no se encuentra registrado"
            )
            return false
        }

        if(!password.equals(registeredPassword)){
            ToastAlert.show(
                this,
                "La contraseña ingresada no es correcta"
            )
            return false
        }

        return true
    }

    override fun onStart() {
        super.onStart()
        Log.d("LoginActivity", "onStart: LoginActivity está en primer plano")
    }

    //resume, pause, stop, destroy
    override fun onResume() {
        super.onResume()
        Log.d("LoginActivity", "onResume: LoginActivity está de vuelta")
    }

    override fun onPause() {
        super.onPause()
        Log.d("LoginActivity", "onPause: LoginActivity está pausada");
    }

    override fun onStop() {
        super.onStop()
        Log.d("LoginActivity", "onStop: LoginActivity está pausada");
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("LoginActivity", "onDestroy: LoginActivity está destruida");
    }
}