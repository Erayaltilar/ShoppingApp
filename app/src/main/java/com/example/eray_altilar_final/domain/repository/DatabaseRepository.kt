package com.example.eray_altilar_final.domain.repository

import com.example.eray_altilar_final.core.Resource
import com.example.eray_altilar_final.domain.model.cartmodel.Cart
import com.example.eray_altilar_final.domain.model.favoritesmodel.Favorites
import com.example.eray_altilar_final.domain.model.productmodel.Product
import com.example.eray_altilar_final.domain.model.productmodel.Products
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    fun addProductToCart(userId: Long, productId: Long, name: String, price: Double, thumbnail: String): Flow<Resource<Cart>>
    fun removeProductFromCart(cartId: Long): Flow<Resource<Unit>>
    fun getProductsInCart(): Flow<Resource<List<Cart>>>
    fun getProductsInFavorites(): Flow<Resource<List<Favorites>>>
    fun addProductToFavorites(userId: Long, productId: Long): Flow<Resource<Favorites>>
    fun removeProductFromFavorites(favoritesId: Long): Flow<Resource<Unit>>
}