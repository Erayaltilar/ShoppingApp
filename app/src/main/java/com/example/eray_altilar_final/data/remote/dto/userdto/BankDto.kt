package com.example.eray_altilar_final.data.remote.dto.userdto

data class BankDto (
    val cardExpire: String,
    val cardNumber: String,
    val cardType: String,
    val currency: String,
    val iban: String
)