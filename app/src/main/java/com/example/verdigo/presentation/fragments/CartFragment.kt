package com.example.verdigo.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clase_nativas.utils.ToastAlert
import com.example.verdigo.R
import com.example.verdigo.data.model.Product
import com.example.verdigo.data.singleton.CartManager
import com.example.verdigo.presentation.fragments.adapters.CartAdapter

class CartFragment: Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var totalItemsText: TextView
    private lateinit var totalPriceText: TextView
    private lateinit var pagarButton: Button
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_carrito)
        totalItemsText = view.findViewById(R.id.totalItemsText)
        totalPriceText = view.findViewById(R.id.totalPriceText)
        pagarButton = view.findViewById(R.id.btnPagar)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        pagarButton.setOnClickListener {
            ToastAlert.show(requireContext(), "Funcionalidad de pago pendiente")
        }

        renderCart()
    }

    private fun renderCart() {
        val items = CartManager.getCartItems()

        cartAdapter = CartAdapter(
            items = items,
            onAdd = {
                CartManager.addToCart(it.product)
                renderCart()
            },
            onRemove = {
                val newQty = it.quantity - 1
                if (newQty > 0) {
                    CartManager.updateQuantity(it.product, newQty)
                } else {
                    CartManager.removeFromCart(it.product)
                }
                renderCart()
            },
            onDelete = {
                CartManager.removeFromCart(it.product)
                renderCart()
            }
        )

        recyclerView.adapter = cartAdapter

        // Actualizar UI con totales
        val totalItems = CartManager.getTotalItemCount()
        val totalPrice = CartManager.getCartTotal()

        totalItemsText.text = "Productos: $totalItems"
        totalPriceText.text = "Total: $%.2f".format(totalPrice)
    }
}