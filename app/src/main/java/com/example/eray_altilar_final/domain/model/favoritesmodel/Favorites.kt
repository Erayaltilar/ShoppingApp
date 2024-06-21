package com.example.eray_altilar_final.domain.model.favoritesmodel

data class Favorites(
    val favoritesId: Long,
    val productId: Long? = null,
    val name: String? = null,
    val thumbnail: String? = null,
    val price: Double? = null,
    val userId: Long? = null,
)