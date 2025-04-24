package com.example.verdigo.presentation.fragments

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
import com.example.verdigo.presentation.fragments.adapters.ProductsAdapter

class FavoritesFragment: Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var recyclerView: RecyclerView
    private lateinit var welcomeTitle: TextView
    private val products = listOf(
        Product(1, "Shampoo Sólid", R.drawable.product, "Cosméticos", 4.0f, "Descripción del producto"),
        Product(2, "Shampoo Sólid", R.drawable.product, "Cosméticos", 4.0f, "Descripción del producto")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)

        //Inicializar variables generadas
        sharedPreferences = requireContext().getSharedPreferences("UserData", MODE_PRIVATE)
        welcomeTitle = view.findViewById(R.id.title_welcome)

        //Asignamos el nombre del usuario
        welcomeTitle.text = getString(R.string.hola_de_nuevo, sharedPreferences.getString("name", ""))

        //Configuración del RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view_products)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = ProductsAdapter(products) { product ->
            redirectToProductDetail(product)
        }
        recyclerView.adapter = adapter

        //Configuración del AvatarFragment
        childFragmentManager.beginTransaction()
            .replace(R.id.avatarFragment, AvatarFragment())
            .commit()

        //Configuración del CategoriesFragment
        childFragmentManager.beginTransaction()
            .replace(R.id.categories_fragment_container, CategoriesFragment())
            .commit()

        return view
    }

    fun redirectToProductDetail(product: Product) {
        val productDetailFragment = ProductDetailFragment()

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, productDetailFragment)
            .addToBackStack(null)
            .commit()
    }
}