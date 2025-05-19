package com.example.verdigo.presentation.fragments.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.verdigo.R
import com.example.verdigo.data.model.Product
import com.example.verdigo.data.singleton.FavoritesManager

class ProductsAdapter(
    private val products: List<Product>,
    private val onItemClicked: (Product) -> Unit
) : RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_product_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product, onItemClicked)
    }

    override fun getItemCount() = products.size

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val favoriteIcon: ImageView = itemView.findViewById(R.id.favorite_icon)

        fun bind(product: Product, onItemClicked: (Product) -> Unit) {
            itemView.findViewById<TextView>(R.id.product_title).text = product.title
            itemView.findViewById<TextView>(R.id.product_category).text = product.category
            itemView.findViewById<ImageView>(R.id.product_image).setImageResource(product.imageResId)
            itemView.findViewById<RatingBar>(R.id.product_rating).rating = product.rating

            // Cambiar visual del icono según si es favorito
            val iconRes = if (FavoritesManager.isProductFavorite(product)) {
                R.drawable.heart_fill_icon
            } else {
                R.drawable.heart_icon
            }
            favoriteIcon.setImageResource(iconRes)

            itemView.setOnClickListener {
                onItemClicked(product)
            }

            // Click en el ícono de favorito
            favoriteIcon.setOnClickListener {
                if (FavoritesManager.isProductFavorite(product)) {
                    FavoritesManager.removeProductFromFavorites(product)
                    favoriteIcon.setImageResource(R.drawable.heart_icon)
                } else {
                    FavoritesManager.addProductToFavorites(product)
                    favoriteIcon.setImageResource(R.drawable.heart_fill_icon)
                }
            }
        }
    }
}