package com.example.verdigo.presentation.fragments

import SmallProductsAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.verdigo.R
import com.example.verdigo.data.model.Product

class SearchFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_search, container, false)

        val products = listOf(
            Product(
                1,
                "Shampoo Sólid",
                R.drawable.product,
                "Cosméticos",
                4.0f,
                "Descripción del producto"
            ),
            Product(
                2,
                "Shampoo Sólid",
                R.drawable.product,
                "Cosméticos",
                4.0f,
                "Descripción del producto"
            )
        )

        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view_products)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = SmallProductsAdapter(products)

        childFragmentManager.beginTransaction()
            .replace(R.id.categories_fragment_container, CategoriesFragment())
            .commit()
        return view
    }


}