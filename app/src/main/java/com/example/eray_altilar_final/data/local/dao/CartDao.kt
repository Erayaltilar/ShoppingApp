package com.example.eray_altilar_final.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.eray_altilar_final.data.local.entity.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart")
    fun getAllCartItems(): Flow<List<CartEntity>>

    @Query("SELECT * FROM cart WHERE userId = :userId")
    fun getCartItemsById(userId: Long): Flow<List<CartEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartEntity)

    @Query("DELETE FROM cart WHERE productId = :productId")
    suspend fun deleteCartItemById(productId: Long)
}
