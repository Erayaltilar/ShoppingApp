package com.example.eray_altilar_final.data.repository

import android.util.Log
import com.example.eray_altilar_final.core.Resource
import com.example.eray_altilar_final.data.local.dao.CartDao
import com.example.eray_altilar_final.data.local.dao.FavoritesDao
import com.example.eray_altilar_final.data.local.entity.CartEntity
import com.example.eray_altilar_final.data.local.entity.FavoritesEntity
import com.example.eray_altilar_final.data.mapper.toCart
import com.example.eray_altilar_final.data.mapper.toFavorites
import com.example.eray_altilar_final.domain.model.cartmodel.Cart
import com.example.eray_altilar_final.domain.model.favoritesmodel.Favorites
import com.example.eray_altilar_final.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val cartDao: CartDao,
    private val favoritesDao: FavoritesDao,
) : DatabaseRepository {
    override fun addProductToCart(userId: Long, productId: Long, name: String, price: Double, thumbnail: String): Flow<Resource<Cart>> = flow {
        try {
            emit(Resource.Loading())
            val cartEntity = CartEntity(userId = userId, productId = productId, name = name, price = price, thumbnail = thumbnail)
            cartDao.insertCartItem(cartEntity)
            Log.d("TAG", "addProductToCart: $cartEntity")
            emit(Resource.Success(cartEntity.toCart()))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage.orEmpty()))
        }
    }

    override fun removeProductFromCart(productId: Long): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())
            val data = cartDao.deleteCartItemById(productId)
            emit(Resource.Success(data))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage.orEmpty()))
        }
    }

    override fun getProductsInCart(): Flow<Resource<List<Cart>>> = flow {
        try {
            emit(Resource.Loading())
            cartDao.getAllCartItems().collect { item ->
                val cartItems = item.map { cartEntity ->
                    cartEntity.toCart()
                }
                emit(Resource.Success(cartItems))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage.orEmpty()))
        }
    }

    override fun getCartByUserId(userId: Long): Flow<Resource<List<Cart>>> = flow {
        try {
            emit(Resource.Loading())
            cartDao.getCartItemsById(userId).collect {
                val cartItems = it.map { cartEntity ->
                    cartEntity.toCart()
                }
                emit(Resource.Success(cartItems))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage.orEmpty()))
        }

    }

    override fun getProductsInFavorites(): Flow<Resource<List<Favorites>>> = flow {
        try {
            emit(Resource.Loading())
            favoritesDao.getAllFavoriteItems().collect { item ->
                val favoritesItems = item.map { favoritesEntity ->
                    favoritesEntity.toFavorites()
                }
                emit(Resource.Success(favoritesItems))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage.orEmpty()))
        }
    }

    override fun getFavoritesByUserId(userId: Long): Flow<Resource<List<Favorites>>> = flow {
        try {
            emit(Resource.Loading())
            favoritesDao.getFavoriteItemById(userId).collect { item ->
                val favoritesItems = item.map { favoritesEntity ->
                    favoritesEntity.toFavorites()
                }
                emit(Resource.Success(favoritesItems))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage.orEmpty()))
        }
    }

    override fun addProductToFavorites(userId: Long, productId: Long, name: String, price: Double, thumbnail: String): Flow<Resource<Favorites>> =
        flow {
            try {
                emit(Resource.Loading())
                val favoritesEntity = FavoritesEntity(userId = userId, productId = productId, name = name, price = price, thumbnail = thumbnail)
                favoritesDao.insertFavoriteItem(favoritesEntity)
                Log.d("TAG", "addProductToFavorites: $favoritesEntity")
                emit(Resource.Success(favoritesEntity.toFavorites()))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage.orEmpty()))
            }
        }

    override fun removeProductFromFavorites(productId: Long): Flow<Resource<Favorites>> = flow {
        try {
            emit(Resource.Loading())
            favoritesDao.deleteFavoriteItemByProductId(productId)
            emit(Resource.Success(Favorites(productId)))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage.orEmpty()))
        }
    }
}
