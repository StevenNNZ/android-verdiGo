package com.example.verdigo.presentation.activities

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.clase_nativas.utils.ToastAlert
import com.example.verdigo.R

class PasswordRecoveryActivity: AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var buttonSend: Button
    private lateinit var buttonBackToLogin: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_recovery)

        Log.d("PasswordRecoveryActivity", "onCreate: Iniciando Activity Password Recovery")

        //Inicializar variables generadas
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE)
        editTextEmail = findViewById(R.id.emailEditText)
        buttonSend = findViewById(R.id.recoveryBtn)
        buttonBackToLogin = findViewById(R.id.backToLoginBtn)

        //Configurar el botón de envío
        buttonSend.setOnClickListener(){
            if(validarCorreo()){
                verificarCorreo()
            }
        }

        //Configurar el botón de regreso
        buttonBackToLogin.setOnClickListener(){
            finish()
        }
    }

    private fun validarCorreo():Boolean{
        val email = editTextEmail.text.toString().trim()

        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.error = "Ingresa un email válido"
            ToastAlert.show(this, "El correo ingresado no es válido")
            return false
        }

        return true
    }

    private fun verificarCorreo(){
        val email = editTextEmail.text.toString().trim()
        val registeredEmail = sharedPreferences.getString("email", "")

        if(email == registeredEmail){
            ToastAlert.show(
                this,
                "Se ha enviado un correo con las instrucciones de reestablecimiento, por favor revisa tu bandeja de entrada"
            )
            //log

            buttonSend.postDelayed({
                finish()
            }, 1500)
            return
        }

        //Mostrar mensaje de error
        ToastAlert.show(
            this,
            "El correo ingresado no corresponde a uno registrado en nuestra aplicación"
        )
    }

    override fun onStart() {
        super.onStart()
        Log.d("PasswordRecoveryActivity", "onStart: PasswordRecoveryActivity está en primer plano")
    }

    //resume, pause, stop, destroy
    override fun onResume() {
        super.onResume()
        Log.d("PasswordRecoveryActivity", "onResume: PasswordRecoveryActivity está de vuelta")
    }

    override fun onPause() {
        super.onPause()
        Log.d("PasswordRecoveryActivity", "onPause: PasswordRecoveryActivity está pausada");
    }

    override fun onStop() {
        super.onStop()
        Log.d("PasswordRecoveryActivity", "onStop: PasswordRecoveryActivity está pausada");
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("PasswordRecoveryActivity", "onDestroy: PasswordRecoveryActivity está destruida");
    }
}