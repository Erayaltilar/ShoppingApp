package com.example.eray_altilar_final.domain.model.usermodel

data class Users (
    val users: List<User>,
    val total: Long,
    val skip: Long,
    val limit: Long
)
