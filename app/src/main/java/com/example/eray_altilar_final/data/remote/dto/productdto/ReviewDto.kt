package com.example.eray_altilar_final.data.remote.dto.productdto

data class ReviewDto (
    val rating: Long,
    val comment: String,
    val date: String,
    val reviewerName: String,
    val reviewerEmail: String
)
