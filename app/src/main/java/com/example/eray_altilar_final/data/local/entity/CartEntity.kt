package com.example.eray_altilar_final.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartEntity(
    @PrimaryKey(autoGenerate = true) val cartId: Long = 0,
    val productId: Long,
    val name: String,
    val thumbnail: String,
    val price: Double,
    val userId: Long
)
