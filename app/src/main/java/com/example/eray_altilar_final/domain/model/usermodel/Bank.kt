package com.example.eray_altilar_final.domain.model.usermodel

data class Bank (
    val cardExpire: String,
    val cardNumber: String,
    val cardType: String,
    val currency: String,
    val iban: String,
)
