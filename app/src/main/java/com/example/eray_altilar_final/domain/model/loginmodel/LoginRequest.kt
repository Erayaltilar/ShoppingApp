package com.example.eray_altilar_final.domain.model.loginmodel

data class LoginRequest(
    val username: String? = null,
    val password: String? = null,
    val expiresInMins: Int = 30,
)
