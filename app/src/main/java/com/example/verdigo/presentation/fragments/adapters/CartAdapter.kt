package com.example.verdigo.presentation.fragments.adapters;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.verdigo.R
import com.example.verdigo.data.model.CartItem;

class CartAdapter(
    private val items: List<CartItem>,
    private val onAdd: (CartItem) -> Unit,
    private val onRemove: (CartItem) -> Unit,
    private val onDelete: (CartItem) -> Unit
) : RecyclerView.Adapter<CartAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_cart_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartItem = items[position]
        holder.bind(cartItem)
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productImage: ImageView = itemView.findViewById(R.id.productImageView)
        private val titleText: TextView = itemView.findViewById(R.id.titleTextView)
        private val priceText: TextView = itemView.findViewById(R.id.priceTextView)
        private val quantityText: TextView = itemView.findViewById(R.id.quantityTextView)
        private val addButton: Button = itemView.findViewById(R.id.addButton)
        private val removeButton: Button = itemView.findViewById(R.id.removeButton)
        private val deleteButton: Button = itemView.findViewById(R.id.deleteButton)

        fun bind(item: CartItem) {
            val product = item.product

            // Establecer imagen local desde recurso
            productImage.setImageResource(product.imageResId)

            // Establecer textos
            titleText.text = product.title
            quantityText.text = item.quantity.toString()
            val subtotal = product.price * item.quantity
            priceText.text = "Subtotal: $%.2f".format(subtotal)

            addButton.setOnClickListener { onAdd(item) }
            removeButton.setOnClickListener { onRemove(item) }
            deleteButton.setOnClickListener { onDelete(item) }
        }
    }
}