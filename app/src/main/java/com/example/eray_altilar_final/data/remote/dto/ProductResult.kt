package com.example.eray_altilar_final.data.remote.dto

import com.example.eray_altilar_final.data.remote.dto.productdto.ProductDto

data class ProductResult (
    val products: List<ProductDto>,
    val total: Long,
    val skip: Long,
    val limit: Long
)
