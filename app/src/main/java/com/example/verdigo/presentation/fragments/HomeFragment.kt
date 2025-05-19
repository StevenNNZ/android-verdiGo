package com.example.verdigo.presentation.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.verdigo.R
import com.example.verdigo.data.model.Product
import com.example.verdigo.data.singleton.CartManager
import com.example.verdigo.presentation.fragments.adapters.ProductsAdapter
import com.google.gson.Gson

class HomeFragment: Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val products =  CartManager.getAvailableProducts()

        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view_products)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = ProductsAdapter(products) { product ->
            redirectToProductDetail(product)
        }

        recyclerView.adapter = adapter

        childFragmentManager.beginTransaction()
            .replace(R.id.categories_fragment_container, CategoriesFragment())
            .commit()

        return view
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