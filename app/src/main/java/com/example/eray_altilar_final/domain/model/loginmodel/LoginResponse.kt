package com.example.eray_altilar_final.domain.model.loginmodel

import com.example.eray_altilar_final.domain.model.usermodel.User

data class LoginResponse(
    val token: String,
    val expires: Long,
    val user: User? = null,
)