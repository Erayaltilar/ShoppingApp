package com.example.eray_altilar_final.domain.model.loginmodel

data class LoginRequest(
    val username: String,
    val password: String,
    val expiresInMins: Int = 30,
)
