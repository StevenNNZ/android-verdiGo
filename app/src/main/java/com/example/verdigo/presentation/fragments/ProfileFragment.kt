package com.example.verdigo.presentation.fragments

import SmallProductsAdapter
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.verdigo.R
import com.example.verdigo.data.model.Product

class ProfileFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var recyclerViewProducts: RecyclerView
    private lateinit var editProfileTextView: TextView
    private lateinit var userNameTextView: TextView
    private lateinit var userDataTextView: TextView
    private val products = listOf(
        Product(1, "Shampoo Sólid", R.drawable.product, "Cosméticos", 4.0f, "Descripción del producto"),
        Product(2, "Shampoo Sólid", R.drawable.product, "Cosméticos", 4.0f, "Descripción del producto")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        //Inicializar variables generadas
        sharedPreferences = requireContext().getSharedPreferences("UserData", MODE_PRIVATE)
        editProfileTextView = view.findViewById(R.id.editProfile)
        userNameTextView = view.findViewById(R.id.userName)
        userDataTextView = view.findViewById(R.id.userData)

        //Asignamos el nombre del usuario
        userNameTextView.text = buildString {
            append(sharedPreferences.getString("name", ""))
            append(" ")
            append(sharedPreferences.getString("lastNames", ""))
        }

        //Asignamos los otros datos
        userDataTextView.text = buildString {
            append(sharedPreferences.getString("email", ""))
            append(" | ")
            append(sharedPreferences.getString("phone", ""))
        }

        //Configuración recyclerView
        recyclerViewProducts = view.findViewById(R.id.recycler_view_products)
        recyclerViewProducts.layoutManager = LinearLayoutManager(context)
        recyclerViewProducts.adapter = SmallProductsAdapter(products)

        //Configuración del AvatarFragment
        childFragmentManager.beginTransaction()
            .replace(R.id.avatarFragment, AvatarFragment())
            .commit()

        // Redirigir al fragmento de edición de perfil
        editProfileTextView.setOnClickListener {
            redirectToEditProfile()
        }

        return view
    }

    private fun redirectToEditProfile() {
        // Crear una nueva instancia del fragmento
        val editProfileFragment = EditProfileFragment()

        // Iniciar la transacción del fragmento
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, editProfileFragment) // Asegúrate de que el ID es correcto
            .addToBackStack(null)  // Añadir a la pila para poder regresar
            .commit()
    }
}