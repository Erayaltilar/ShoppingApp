package com.example.eray_altilar_final.domain.model.usermodel

data class User (
    var id: Long,
    val firstName: String,
    val lastName: String,
    val age: Long,
    val gender: String,
    val email: String,
    val phone: String,
    val username: String,
    val password: String,
    val birthDate: String,
    val image: String,
    val bloodGroup: String,
    val height: Double,
    val weight: Double,
    val address: Address,
)
