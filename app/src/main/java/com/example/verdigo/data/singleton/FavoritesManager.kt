package com.example.verdigo.data.singleton

import android.content.Context
import android.content.SharedPreferences
import com.example.verdigo.data.model.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object FavoritesManager {

    private const val PREF_NAME = "CartPrefs"
    private val gson = Gson()
    private lateinit var sharedPreferences: SharedPreferences
    private const val KEY_FAVORITES = "favorite_products"
    private val favoriteProducts = mutableListOf<Product>()

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        loadFavoritesFromPrefs()
    }

    fun addProductToFavorites(product: Product) {
        if (!favoriteProducts.any { it.id == product.id }) {
            favoriteProducts.add(product)
            saveFavoritesToPrefs()
        }
    }

    fun removeProductFromFavorites(product: Product) {
        favoriteProducts.removeAll { it.id == product.id }
        saveFavoritesToPrefs()
    }

    fun isProductFavorite(product: Product): Boolean {
        return favoriteProducts.any { it.id == product.id }
    }

    fun getFavoriteProducts(): List<Product> {
        return favoriteProducts.toList()
    }

    private fun saveFavoritesToPrefs() {
        val json = gson.toJson(favoriteProducts)
        sharedPreferences.edit().putString(KEY_FAVORITES, json).apply()
    }

    fun loadFavoritesFromPrefs() {
        val json = sharedPreferences.getString(KEY_FAVORITES, null)
        if (!json.isNullOrEmpty()) {
            val type = object : TypeToken<List<Product>>() {}.type
            val loaded: List<Product> = gson.fromJson(json, type)
            favoriteProducts.clear()
            favoriteProducts.addAll(loaded)
        }
    }

}