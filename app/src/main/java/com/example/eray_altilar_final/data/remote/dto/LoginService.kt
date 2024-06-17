package com.example.eray_altilar_final.data.remote.dto

import com.example.eray_altilar_final.data.remote.dto.userdto.UserDto

data class LoginRequestDto(
    val username: String,
    val password: String,
    val expiresInMins: Int = 30
)

data class LoginResponseDto(
    val token: String,
    val expires: Long,
    val user: UserDto? = null,
)
