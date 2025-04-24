package com.example.verdigo.presentation.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.Fragment
import com.example.clase_nativas.utils.ToastAlert
import com.example.verdigo.R

class EditProfileFragment: Fragment()  {

    private lateinit var edtNombres: EditText
    private lateinit var edtApellidos: EditText
    private lateinit var edtCorreo: EditText
    private lateinit var edtTelefono: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtVerPassword: EditText
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var btnGuardarDatos: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)

        //Inicializar las variables
        sharedPreferences = requireContext().getSharedPreferences("UserData", MODE_PRIVATE)
        edtNombres = view.findViewById(R.id.edtNombresPerfil)
        edtApellidos = view.findViewById(R.id.edtApellidosPerfil)
        edtCorreo = view.findViewById(R.id.edtCorreoPerfil)
        edtTelefono = view.findViewById(R.id.edtTelefonoPerfil)
        edtPassword = view.findViewById(R.id.edtPasswordPerfil)
        edtVerPassword = view.findViewById(R.id.edtVerPasswordPerfil)
        btnGuardarDatos = view.findViewById(R.id.btnGuardarDatos)

        //Cargamos datos
        cargarDatosUsuario()

        //Configuración del avatar
        childFragmentManager.beginTransaction()
            .replace(R.id.avatarFragment, AvatarFragment())
            .commit()

        //Configuración listener botón de registro
        btnGuardarDatos.setOnClickListener(){
            if(validateFields()){
                //método de guardar datos de usuario
                guardarDatosUsuario()

                //redireccionamiento
                redirectToProfile()
            }
        }

        return view
    }

    fun cargarDatosUsuario() {
        val name = sharedPreferences.getString("name", "")
        val lastNames = sharedPreferences.getString("lastNames", "")
        val email = sharedPreferences.getString("email", "")
        val phone = sharedPreferences.getString("phone", "")

        edtNombres.setText(name)
        edtApellidos.setText(lastNames)
        edtCorreo.setText(email)
        edtTelefono.setText(phone)
    }

    private fun validateFields(): Boolean {
        val name = edtNombres.text.toString().trim()
        val lastNames = edtApellidos.text.toString().trim()
        val email = edtCorreo.text.toString().trim()
        val phone = edtTelefono.text.toString().trim()
        val password = edtPassword.text.toString().trim()
        val repeatPassword = edtVerPassword.text.toString().trim()

        var isValid = true

        // Expresiones regulares para validación
        val nameRegex = Regex("^[A-Za-zÁÉÍÓÚáéíóúñÑ ]{2,50}$")  // Solo letras, espacios y longitud 2-50
        val passwordRegex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}$") // Min 8, Max 20, al menos 1 letra y 1 número

        // Validación de nombres
        if (name.isEmpty()) {
            edtNombres.error = "El campo Nombres es requerido"
            isValid = false
        } else if (!name.matches(nameRegex)) {
            edtNombres.error = "El nombre debe tener entre 2 y 50 caracteres y solo letras"
            isValid = false
        }

        // Validación de apellidos
        if (lastNames.isEmpty()) {
            edtApellidos.error = "El campo Apellidos es requerido"
            isValid = false
        } else if (!lastNames.matches(nameRegex)) {
            edtApellidos.error = "Los apellidos deben tener entre 2 y 50 caracteres y solo letras"
            isValid = false
        }

        // Validación de email
        if (email.isEmpty()) {
            edtCorreo.error = "El campo Email es requerido"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtCorreo.error = "Ingresa un email válido"
            isValid = false
        }

        // Validación de teléfono
        if (phone.isEmpty()) {
            edtTelefono.error = "El campo Celular es requerido"
            isValid = false
        } else if (!phone.matches(Regex("^\\d{9,15}$"))) { // Solo números, entre 9 y 15 caracteres
            edtTelefono.error = "El número de celular debe tener entre 9 y 15 dígitos"
            isValid = false
        }

        // Validación de contraseña
        if (password.isNotEmpty() && !password.matches(passwordRegex)) {
            edtPassword.error = "La contraseña debe tener entre 8 y 20 caracteres, incluir al menos una letra y un número"
            isValid = false
        }

        // Validación de repetición de contraseña
        if (password.isNotEmpty() && repeatPassword.isEmpty()) {
            edtVerPassword.error = "El campo Repetir contraseña es requerido"
            isValid = false
        } else if (!password.equals(repeatPassword)) {
            edtVerPassword.error = "Las contraseñas no coinciden"
            isValid = false
        }

        return isValid
    }

    private fun guardarDatosUsuario(){
        val editor = sharedPreferences.edit()
        editor.putString("name", edtNombres.text.toString().trim())
        editor.putString("lastNames", edtApellidos.text.toString().trim())
        editor.putString("email", edtCorreo.text.toString().trim())
        editor.putString("phone", edtTelefono.text.toString().trim())

        val password = edtPassword.text.toString().trim()
        if(password.isNotEmpty()){
            editor.putString("password", password)
        }
        editor.apply()
        ToastAlert.show(requireContext(), "Actualización de datos exitosa")
    }

    private fun redirectToProfile() {
        // Crear una nueva instancia del fragmento
        val profileFragment = ProfileFragment()

        // Iniciar la transacción del fragmento
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, profileFragment) // Asegúrate de que el ID es correcto
            .addToBackStack(null)  // Añadir a la pila para poder regresar
            .commit()
    }
}