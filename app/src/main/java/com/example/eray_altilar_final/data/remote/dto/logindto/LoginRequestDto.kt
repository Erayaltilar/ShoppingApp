package com.example.eray_altilar_final.data.remote.dto.logindto

data class LoginRequestDto(
    val username: String,
    val password: String,
    val expiresInMins: Int = 30
)
