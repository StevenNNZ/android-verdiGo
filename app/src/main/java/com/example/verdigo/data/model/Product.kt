package com.example.verdigo.data.model

data class Product(
    val id: Int,
    val title: String,
    val imageResId: Int,
    val category: String,
    val rating: Float,
    val price: Double,
    val description: String
)
