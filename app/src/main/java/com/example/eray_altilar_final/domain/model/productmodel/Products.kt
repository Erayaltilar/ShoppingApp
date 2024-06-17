package com.example.eray_altilar_final.domain.model.productmodel

data class Products (
    val products: List<Product>,
    val total: Long,
    val skip: Long,
    val limit: Long
)