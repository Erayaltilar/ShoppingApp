package com.example.eray_altilar_final.data.mapper

import com.example.eray_altilar_final.data.remote.dto.LoginRequestDto
import com.example.eray_altilar_final.data.remote.dto.LoginResponseDto
import com.example.eray_altilar_final.domain.model.LoginRequest
import com.example.eray_altilar_final.domain.model.LoginResponse

fun LoginRequest.toLoginRequestDto() : LoginRequestDto {
    return LoginRequestDto(
        username = username,
        password = password,
        expiresInMins = expiresInMins,
    )
}

fun LoginResponseDto.toLoginResponse() : LoginResponse {
    return LoginResponse(
        token = token,
        expires = expires,
        user = user?.toUser(),
    )
}
