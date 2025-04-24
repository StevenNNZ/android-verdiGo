package com.example.verdigo.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.verdigo.R

class ProductsFragment : Fragment() {

    private var isFavorite = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product_item, container, false)

        val favoriteIcon = view.findViewById<ImageView>(R.id.favorite_icon)

        favoriteIcon.setOnClickListener {
            isFavorite = !isFavorite
            if (isFavorite) {
                favoriteIcon.setImageResource(R.drawable.heart_fill_icon)
                favoriteIcon.setColorFilter(resources.getColor(R.color.principal, null))
            } else {
                favoriteIcon.setImageResource(R.drawable.heart_icon)
                favoriteIcon.setColorFilter(resources.getColor(R.color.txtCard, null))
            }
        }


        return view
    }
}
