package com.example.eray_altilar_final.domain.model.productmodel

data class Review (
    val rating: Long,
    val comment: String,
    val date: String,
    val reviewerName: String,
    val reviewerEmail: String
)