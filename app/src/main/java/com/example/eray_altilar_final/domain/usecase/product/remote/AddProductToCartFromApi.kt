package com.example.eray_altilar_final.domain.usecase.product.remote

import com.example.eray_altilar_final.core.Resource
import com.example.eray_altilar_final.domain.model.productmodel.Product
import com.example.eray_altilar_final.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddProductToCartFromApi @Inject constructor(
    private val productRepository: ProductRepository,
) {
    operator fun invoke(productId: Long): Flow<Resource<Product>> {
        return productRepository.addProductToCartFromApi(productId)
    }
}