package com.example.eray_altilar_final.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoritesEntity(
    @PrimaryKey(autoGenerate = true) val favoritesId: Long = 0,
    val productId: Long? = null,
    val name: String? = null,
    val thumbnail: String? = null,
    val price: Double? = null,
    val userId: Long? = null,
)
