package com.example.eray_altilar_final.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.eray_altilar_final.data.local.entity.FavoritesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM favorites")
    fun getAllFavoriteItems(): Flow<List<FavoritesEntity>>

    @Query("SELECT * FROM favorites WHERE userId = :userId")
    fun getFavoriteItemById(userId: Long):  Flow<List<FavoritesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteItem(favoriteItem: FavoritesEntity)

    @Query("DELETE FROM favorites WHERE productId = :productId")
    suspend fun deleteFavoriteItemByProductId(productId: Long)
}