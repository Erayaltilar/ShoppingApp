package com.example.eray_altilar_final.domain.repository

import com.example.eray_altilar_final.core.Resource
import com.example.eray_altilar_final.domain.model.cartmodel.Cart
import com.example.eray_altilar_final.domain.model.favoritesmodel.Favorites
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    fun addProductToCart(userId: Long, productId: Long, name: String, price: Double, thumbnail: String): Flow<Resource<Cart>>
    fun removeProductFromCart(productId: Long): Flow<Resource<Unit>>
    fun getProductsInCart(): Flow<Resource<List<Cart>>>
    fun getProductsInFavorites(): Flow<Resource<List<Favorites>>>
    fun getFavoritesByUserId(userId: Long): Flow<Resource<List<Favorites>>>
    fun addProductToFavorites(userId: Long, productId: Long, name: String, price: Double, thumbnail: String): Flow<Resource<Favorites>>
    fun removeProductFromFavorites(productId: Long): Flow<Resource<Favorites>>
}