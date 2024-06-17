package com.example.eray_altilar_final.domain.model.cartmodel


data class Cart(
    val cartId: Long,
    val productId: Long,
    val name: String,
    val thumbnail: String,
    val price: Double,
    val userId: Long
)