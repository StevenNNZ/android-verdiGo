package com.example.verdigo.data.singleton

import android.content.Context
import android.content.SharedPreferences
import com.example.verdigo.R
import com.example.verdigo.data.model.CartItem
import com.example.verdigo.data.model.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object CartManager {

    private const val PREF_NAME = "CartPrefs"
    private const val KEY_PRODUCTS_LIST = "productsList"
    private const val KEY_CART_ITEMS = "cart_items"
    private lateinit var sharedPreferences: SharedPreferences
    private val cartItems = mutableListOf<CartItem>()
    private val gson = Gson()

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        loadCartFromPrefs()
        initProducts(context)
    }

    fun addToCart(product: Product) {
        val item = cartItems.find { it.product.id == product.id }
        if (item != null) {
            item.quantity++
        } else {
            cartItems.add(CartItem(product, 1))
        }
        saveCartToPrefs()
    }

    fun removeFromCart(product: Product) {
        val item = cartItems.find { it.product.id == product.id }
        if (item != null) {
            cartItems.remove(item)
            saveCartToPrefs()
        }
    }

    fun updateQuantity(product: Product, quantity: Int) {
        val item = cartItems.find { it.product.id == product.id }
        if (item != null) {
            if (quantity > 0) {
                item.quantity = quantity
            } else {
                cartItems.remove(item)
            }
            saveCartToPrefs()
        }
    }

    fun clearCart() {
        cartItems.clear()
        saveCartToPrefs()
    }

    fun getCartItems(): List<CartItem> {
        return cartItems.toList()
    }

    fun getTotalItemCount(): Int {
        return cartItems.sumOf { it.quantity }
    }

    fun getCartTotal(): Float {
        return cartItems.sumOf { it.quantity * it.product.price.toDouble() }.toFloat()
    }

    private fun saveCartToPrefs() {
        val json = gson.toJson(cartItems)
        sharedPreferences.edit().putString(KEY_CART_ITEMS, json).apply()
    }

    private fun loadCartFromPrefs() {
        val json = sharedPreferences.getString(KEY_CART_ITEMS, null)
        if (!json.isNullOrEmpty()) {
            val type = object : TypeToken<List<CartItem>>() {}.type
            val savedItems: List<CartItem> = gson.fromJson(json, type)
            cartItems.clear()
            cartItems.addAll(savedItems)
        }
    }

    fun initProducts(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val existing = sharedPreferences.getString(KEY_PRODUCTS_LIST, null)

        if (existing != null) return // Productos ya guardados

        val products = listOf(
            Product(
                id = 1,
                title = "Shampoo Sólido",
                imageResId = R.drawable.product,
                category = "Cuidado Personal",
                rating = 4.2f,
                price = 15000.00,
                description = "Shampoo ecológico libre de plásticos."
            ),
            Product(
                id = 2,
                title = "Jabón Artesanal",
                imageResId = R.drawable.product,
                category = "Cuidado Personal",
                rating = 4.5f,
                price = 8000.00,
                description = "Jabón hecho a mano con ingredientes naturales."
            ),
            Product(
                id = 3,
                title = "Bolsa Reutilizable",
                imageResId = R.drawable.product,
                category = "Accesorios",
                rating = 4.0f,
                price = 12000.00,
                description = "Bolsa resistente para tus compras ecológicas."
            ),
            Product(
                id = 4,
                title = "Botella de Acero Inoxidable",
                imageResId = R.drawable.product,
                category = "Accesorios",
                rating = 4.8f,
                price = 30000.00,
                description = "Mantiene la temperatura de tus bebidas hasta por 12 horas."
            ),
            Product(
                id = 5,
                title = "Cepillo de Bambú",
                imageResId = R.drawable.product,
                category = "Cuidado Dental",
                rating = 3.9f,
                price = 5000.00,
                description = "Alternativa ecológica al cepillo tradicional de plástico."
            )
        )

        val json = gson.toJson(products)
        sharedPreferences.edit().putString(KEY_PRODUCTS_LIST, json).apply()
    }

    // Usar para cargar los productos del catálogo
    fun getAvailableProducts(): List<Product> {
        val json = sharedPreferences.getString(KEY_PRODUCTS_LIST, null)
        return if (!json.isNullOrEmpty()) {
            val type = object : TypeToken<List<Product>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }
}