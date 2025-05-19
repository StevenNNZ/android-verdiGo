package com.example.verdigo.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.verdigo.R

class HomeActivity: AppCompatActivity() {

    private lateinit var buttonLogin: Button
    private lateinit var buttonRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        // Inicializamos variables
        buttonLogin = findViewById(R.id.loginButtonHome)
        buttonRegister = findViewById(R.id.signUpButtonHome)

        // Redirigimos al formulario de login
        buttonLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent) // Iniciar la segunda actividad
        }

        //Redirigimos al formulario de registro
        buttonRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent) // Iniciar la segunda actividad
        }
    }
}