package com.example.verdigo.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.clase_nativas.utils.ToastAlert
import com.example.verdigo.R
import com.example.verdigo.data.model.Product
import com.example.verdigo.data.singleton.CartManager
import com.google.gson.Gson

class ProductDetailFragment: Fragment() {

    private var selectedProduct: Product? = null
    private var quantity = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtener referencias de vistas
        val imageView = view.findViewById<ImageView>(R.id.productImage)
        val titleText = view.findViewById<TextView>(R.id.productTitle)
        val categoryText = view.findViewById<TextView>(R.id.productCategory)
        val priceText = view.findViewById<TextView>(R.id.productPrice)
        val descriptionText = view.findViewById<TextView>(R.id.productDescription)
        val quantityText = view.findViewById<TextView>(R.id.quantityText)
        val incrementButton = view.findViewById<Button>(R.id.incrementButton)
        val decrementButton = view.findViewById<Button>(R.id.decrementButton)
        val addToCartButton = view.findViewById<Button>(R.id.addToCartButton)

        // Recuperar producto desde SharedPreferences
        val prefs = requireContext().getSharedPreferences("SelectedProduct", Context.MODE_PRIVATE)
        val json = prefs.getString("selected_product", null)

        json?.let {
            selectedProduct = Gson().fromJson(it, Product::class.java)
        }

        selectedProduct?.let { product ->

            // Renderizar datos
            titleText.text = product.title
            categoryText.text = product.category
            priceText.text = "$${product.price}"
            descriptionText.text = product.description
            imageView.setImageResource(product.imageResId)

            // Configurar cantidad (+ / -)
            quantityText.text = quantity.toString()

            incrementButton.setOnClickListener {
                quantity++
                quantityText.text = quantity.toString()
            }

            decrementButton.setOnClickListener {
                if (quantity > 1) {
                    quantity--
                    quantityText.text = quantity.toString()
                }
            }

            // Agregar al carrito
            addToCartButton.setOnClickListener {
                repeat(quantity) {
                    CartManager.addToCart(product)
                }
                ToastAlert.show(requireContext(), "Agregado al carrito")
            }
        } ?: run {
            ToastAlert.show(requireContext(), "No se pudo cargar el producto")
        }
    }
}