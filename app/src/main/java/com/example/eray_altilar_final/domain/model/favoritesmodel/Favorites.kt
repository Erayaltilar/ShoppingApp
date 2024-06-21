package com.example.eray_altilar_final.domain.model.favoritesmodel

data class Favorites(
    val favoritesId: Long,
    val productId: Long,
    val name: String,
    val thumbnail: String,
    val price: Double,
    val userId: Long
)