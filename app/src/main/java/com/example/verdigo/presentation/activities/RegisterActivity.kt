package com.example.verdigo.presentation.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.clase_nativas.utils.ToastAlert
import com.example.verdigo.R

class RegisterActivity: AppCompatActivity() {

    private lateinit var editTextNames: EditText
    private lateinit var editTextLastNames: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextRepeatPassword: EditText
    private lateinit var checkBoxTyC: CheckBox
    private lateinit var buttonRegister: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        Log.d("RegisterActivity", "onCreate: Registro Activity iniciado")

        //Inicializar las variables
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE)

        //Inicializar variables de vistas
        editTextNames = findViewById(R.id.editTextNames)
        editTextLastNames = findViewById(R.id.editTextLastNames)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPhone = findViewById(R.id.editTextPhone)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextRepeatPassword = findViewById(R.id.editTextRepeatPassword)
        checkBoxTyC = findViewById(R.id.checkBoxTyC)
        buttonRegister = findViewById(R.id.btnRegister)

        //Configuración listener botón de registro
        buttonRegister.setOnClickListener(){
            if(validateFields()){
                //método de guardar datos de usuario
                guardarDatosUsuario()

                //redireccionamiento
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        //Iniciar sesión
        val redirectLoginTextView: TextView = findViewById(R.id.redirectLoginTextView)
        redirectLoginTextView.setOnClickListener {
            redirectToLogin()
        }
    }

    private fun validateFields(): Boolean {
        val name = editTextNames.text.toString().trim()
        val lastNames = editTextLastNames.text.toString().trim()
        val email = editTextEmail.text.toString().trim()
        val phone = editTextPhone.text.toString().trim()
        val password = editTextPassword.text.toString().trim()
        val repeatPassword = editTextRepeatPassword.text.toString().trim()
        val checkBoxTyC = checkBoxTyC.isChecked

        var isValid = true

        // Expresiones regulares para validación
        val nameRegex = Regex("^[A-Za-zÁÉÍÓÚáéíóúñÑ ]{2,50}$")  // Solo letras, espacios y longitud 2-50
        val passwordRegex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}$") // Min 8, Max 20, al menos 1 letra y 1 número

        // Validación de nombres
        if (name.isEmpty()) {
            editTextNames.error = "El campo Nombres es requerido"
            isValid = false
        } else if (!name.matches(nameRegex)) {
            editTextNames.error = "El nombre debe tener entre 2 y 50 caracteres y solo letras"
            isValid = false
        }

        // Validación de apellidos
        if (lastNames.isEmpty()) {
            editTextLastNames.error = "El campo Apellidos es requerido"
            isValid = false
        } else if (!lastNames.matches(nameRegex)) {
            editTextLastNames.error = "Los apellidos deben tener entre 2 y 50 caracteres y solo letras"
            isValid = false
        }

        // Validación de email
        if (email.isEmpty()) {
            editTextEmail.error = "El campo Email es requerido"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.error = "Ingresa un email válido"
            isValid = false
        }

        // Validación de teléfono
        if (phone.isEmpty()) {
            editTextPhone.error = "El campo Celular es requerido"
            isValid = false
        } else if (!phone.matches(Regex("^\\d{9,15}$"))) { // Solo números, entre 9 y 15 caracteres
            editTextPhone.error = "El número de celular debe tener entre 9 y 15 dígitos"
            isValid = false
        }

        // Validación de contraseña
        if (password.isEmpty()) {
            editTextPassword.error = "El campo Contraseña es requerido"
            isValid = false
        } else if (!password.matches(passwordRegex)) {
            editTextPassword.error = "La contraseña debe tener entre 8 y 20 caracteres, incluir al menos una letra y un número"
            isValid = false
        }

        // Validación de repetición de contraseña
        if (repeatPassword.isEmpty()) {
            editTextRepeatPassword.error = "El campo Repetir contraseña es requerido"
            isValid = false
        } else if (!password.equals(repeatPassword)) {
            editTextRepeatPassword.error = "Las contraseñas no coinciden"
            isValid = false
        }

        if(!isValid){
            ToastAlert.show(this,"Hay errores en el formulario, por favor verifique cada uno para poder continuar.")
        }else if (!checkBoxTyC) {
            // Validación de checkbox términos y condiciones
            ToastAlert.show(this,"Debes aceptar los términos y condiciones para continuar")
            isValid = false
        }

        return isValid
    }

    private fun guardarDatosUsuario(){
        val editor = sharedPreferences.edit()
        editor.putString("name", editTextNames.text.toString().trim())
        editor.putString("lastNames", editTextLastNames.text.toString().trim())
        editor.putString("email", editTextEmail.text.toString().trim())
        editor.putString("phone", editTextPhone.text.toString().trim())
        editor.putString("password", editTextPassword.text.toString().trim())
        editor.apply()
        Log.d("Register Activity", "guardarDatosUsuario: Datos del usuario guardados")
        ToastAlert.show(this, "Registro exitoso")
    }

    private fun redirectToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        Log.d("RegisterActivity", "onStart: RegisterActivity está en primer plano")
    }

    //resume, pause, stop, destroy
    override fun onResume() {
        super.onResume()
        Log.d("RegisterActivity", "onResume: RegisterActivity está de vuelta")
    }

    override fun onPause() {
        super.onPause()
        Log.d("RegisterActivity", "onPause: RegisterActivity está pausada");
    }

    override fun onStop() {
        super.onStop()
        Log.d("RegisterActivity", "onStop: RegisterActivity está pausada");
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("RegisterActivity", "onDestroy: RegisterActivity está destruida");
    }
}