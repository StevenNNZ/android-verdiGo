package com.example.verdigo.presentation.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.Fragment
import com.example.verdigo.R

class StartFragment: Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var tiendaButton: LinearLayout
    private lateinit var perfilButton: LinearLayout
    private lateinit var welcomeTitle: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_start, container, false)

        //Inicializar variables generadas
        sharedPreferences = requireContext().getSharedPreferences("UserData", MODE_PRIVATE)
        tiendaButton = view.findViewById(R.id.buttonShop)
        perfilButton = view.findViewById(R.id.buttonProfile)
        welcomeTitle = view.findViewById(R.id.welcomeTitle)

        //Asignamos el nombre del usuario
        welcomeTitle.text = getString(R.string.bienvenido, sharedPreferences.getString("name", ""))

        //Eventos
        tiendaButton.setOnClickListener {
            navigateToFragment(HomeFragment())
        }

        perfilButton.setOnClickListener {
            navigateToFragment(ProfileFragment())
        }

        return view
    }

    private fun navigateToFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment) // Asegúrate de que el ID es correcto
            .addToBackStack(null) // Opcional, si deseas permitir al usuario volver
            .commit()
    }
}