package com.example.verdigo.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.verdigo.R
import com.example.verdigo.data.model.Product
import com.example.verdigo.presentation.fragments.adapters.ProductsAdapter

class HomeFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val products = listOf(
            Product(1, "Shampoo Sólid", R.drawable.product, "Cosméticos", 4.0f, "Descripción del producto"),
            Product(2, "Shampoo Sólid", R.drawable.product, "Cosméticos", 4.0f, "Descripción del producto")
        )

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
        val productDetailFragment = ProductDetailFragment()

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, productDetailFragment)
            .addToBackStack(null)
            .commit()
    }
}