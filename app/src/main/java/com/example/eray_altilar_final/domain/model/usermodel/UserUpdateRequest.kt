package com.example.eray_altilar_final.domain.model.usermodel

data class UserUpdateRequest(
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val username: String? = null,
)