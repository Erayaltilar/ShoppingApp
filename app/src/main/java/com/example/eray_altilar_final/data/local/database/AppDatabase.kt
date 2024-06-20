package com.example.eray_altilar_final.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.eray_altilar_final.data.local.dao.CartDao
import com.example.eray_altilar_final.data.local.entity.CartEntity


@Database(entities = [CartEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cartDao(): CartDao
}