package com.example.eray_altilar_final.data.remote.dto

import com.example.eray_altilar_final.data.remote.dto.userdto.UserDto

data class UserResult (
    val users: List<UserDto>,
    val total: Long,
    val skip: Long,
    val limit: Long,
)