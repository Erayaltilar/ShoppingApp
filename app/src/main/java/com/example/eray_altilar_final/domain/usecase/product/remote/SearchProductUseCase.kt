package com.example.eray_altilar_final.domain.usecase.product.remote

import com.example.eray_altilar_final.core.Resource
import com.example.eray_altilar_final.domain.model.productmodel.Product
import com.example.eray_altilar_final.domain.model.productmodel.Products
import com.example.eray_altilar_final.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchProductUseCase @Inject constructor(
    private val productRepository: ProductRepository,
) {

    operator fun invoke(query: String): Flow<Resource<Products>> {
        return productRepository.searchProduct(query)
    }
}