package com.example.eray_altilar_final.domain.usecase

import com.example.eray_altilar_final.core.Resource
import com.example.eray_altilar_final.domain.model.productmodel.Products
import com.example.eray_altilar_final.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(private val productRepository: ProductRepository) {

    operator fun invoke(limit: Int, skip: Int): Flow<Resource<Products>> {
        return productRepository.getProducts(limit, skip)
    }
}