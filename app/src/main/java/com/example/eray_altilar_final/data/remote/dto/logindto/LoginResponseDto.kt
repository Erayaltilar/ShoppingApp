package com.example.eray_altilar_final.data.remote.dto.logindto

import com.example.eray_altilar_final.data.remote.dto.userdto.UserDto

data class LoginResponseDto(
    val token: String,
    val expires: Long,
    val user: UserDto? = null,
)
