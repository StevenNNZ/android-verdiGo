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
import com.example.verdigo.data.singleton.FavoritesManager
import com.example.verdigo.presentation.fragments.adapters.ProductsAdapter
import com.google.gson.Gson

class FavoritesFragment: Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var recyclerView: RecyclerView
    private lateinit var welcomeTitle: TextView
    private lateinit var productsAdapter: ProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)


        //Configuración del RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view_products)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Obtener productos favoritos del singleton
        val favoriteProducts = FavoritesManager.getFavoriteProducts()
        productsAdapter = ProductsAdapter(favoriteProducts) { product ->
            redirectToProductDetail(product)
        }
        recyclerView.adapter = productsAdapter

        //Inicializar variables generadas
        sharedPreferences = requireContext().getSharedPreferences("UserData", MODE_PRIVATE)
        welcomeTitle = view.findViewById(R.id.title_welcome)

        //Asignamos el nombre del usuario
        welcomeTitle.text = getString(R.string.hola_de_nuevo, sharedPreferences.getString("name", ""))

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

    override fun onResume() {
        super.onResume()
        val updatedFavorites = FavoritesManager.getFavoriteProducts()
        productsAdapter = ProductsAdapter(updatedFavorites) { product ->
            redirectToProductDetail(product)
        }
        recyclerView.adapter = productsAdapter
    }

    fun redirectToProductDetail(product: Product) {
        // Guardamos el producto seleccionado como JSON
        val prefs = requireContext().getSharedPreferences("SelectedProduct", MODE_PRIVATE)
        val editor = prefs.edit()
        val gson = Gson()
        val productJson = gson.toJson(product)
        editor.putString("selected_product", productJson)
        editor.apply()

        // Navegar al fragmento de detalle
        val productDetailFragment = ProductDetailFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, productDetailFragment)
            .addToBackStack(null)
            .commit()
    }
}