package com.example.eray_altilar_final.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.eray_altilar_final.data.local.dao.CartDao
import com.example.eray_altilar_final.data.local.dao.FavoritesDao
import com.example.eray_altilar_final.data.local.entity.CartEntity
import com.example.eray_altilar_final.data.local.entity.FavoritesEntity


@Database(entities = [CartEntity::class, FavoritesEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cartDao(): CartDao

    abstract fun favoritesDao(): FavoritesDao
}