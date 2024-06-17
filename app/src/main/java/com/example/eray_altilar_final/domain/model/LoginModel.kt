package com.example.eray_altilar_final.domain.model

import com.example.eray_altilar_final.domain.model.usermodel.User

data class LoginRequest(
    val username: String,
    val password: String,
    val expiresInMins: Int = 30,
)

data class LoginResponse(
    val token: String,
    val expires: Long,
    val user: User? = null,
)