package com.example.verdigo.presentation.fragments

import SmallProductsAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clase_nativas.utils.ToastAlert
import com.example.verdigo.R
import com.example.verdigo.data.model.Product
import com.example.verdigo.data.singleton.CartManager
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SearchFragment: Fragment() {

    private lateinit var products: List<Product>
    private lateinit var allProducts: List<Product>
    private lateinit var adapter: SmallProductsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchInput: TextInputEditText
    private lateinit var searchLayout: TextInputLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        // Obtener todos los productos disponibles
        allProducts = CartManager.getAvailableProducts()
        products = allProducts // copia inicial sin filtro

        // Inicializar vistas
        searchLayout = view.findViewById(R.id.search_layout)
        searchInput = view.findViewById(R.id.searchProductFilter)
        recyclerView = view.findViewById(R.id.recycler_view_products)

        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = SmallProductsAdapter(products)
        recyclerView.adapter = adapter

        searchLayout.setEndIconOnClickListener {
            val query = searchInput.text.toString().trim()
            if (query.isEmpty()) {
                ToastAlert.show(requireContext(), "Ingresa un término de búsqueda")
                return@setEndIconOnClickListener
            }

            // Filtrar productos por nombre, categoría o descripción
            val filteredProducts = allProducts.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.category.contains(query, ignoreCase = true) ||
                        it.description.contains(query, ignoreCase = true)
            }

            adapter = SmallProductsAdapter(filteredProducts)
            recyclerView.adapter = adapter

            if (filteredProducts.isEmpty()) {
                ToastAlert.show(requireContext(), "Sin resultados para \"$query\"")
            }
        }

        // Cargar fragmento de categorías
        childFragmentManager.beginTransaction()
            .replace(R.id.categories_fragment_container, CategoriesFragment())
            .commit()

        return view
    }
}