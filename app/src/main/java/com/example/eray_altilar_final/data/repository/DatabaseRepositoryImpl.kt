package com.example.eray_altilar_final.data.repository

import android.util.Log
import com.example.eray_altilar_final.core.Resource
import com.example.eray_altilar_final.data.local.dao.CartDao
import com.example.eray_altilar_final.data.local.entity.CartEntity
import com.example.eray_altilar_final.data.mapper.toCart
import com.example.eray_altilar_final.data.remote.ProductService
import com.example.eray_altilar_final.domain.model.cartmodel.Cart
import com.example.eray_altilar_final.domain.model.productmodel.Product
import com.example.eray_altilar_final.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val cartDao: CartDao,
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

    override fun removeProductFromCart(cartId: Long): Flow<Resource<Unit>> {
        TODO("Not yet implemented")
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
}
