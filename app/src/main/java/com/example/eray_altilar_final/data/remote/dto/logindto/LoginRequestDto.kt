package com.example.eray_altilar_final.data.remote.dto.logindto

data class LoginRequestDto(
    val username: String? = null,
    val password: String? = null,
    val expiresInMins: Int = 30
)
