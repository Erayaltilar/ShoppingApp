package com.example.eray_altilar_final.domain.usecase.product.database

import com.example.eray_altilar_final.core.Resource
import com.example.eray_altilar_final.domain.model.cartmodel.Cart
import com.example.eray_altilar_final.domain.model.productmodel.Product
import com.example.eray_altilar_final.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddProductToCartUseCase @Inject constructor(
    private val repository: DatabaseRepository
) {
    operator fun invoke(userId: Long, productId: Long, name: String, price: Double, thumbnail: String): Flow<Resource<Cart>> {
        return repository.addProductToCart(userId,productId,name,price,thumbnail)
    }
}
