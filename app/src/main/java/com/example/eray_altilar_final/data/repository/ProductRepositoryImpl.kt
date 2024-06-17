package com.example.eray_altilar_final.data.repository

import com.example.eray_altilar_final.core.Resource
import com.example.eray_altilar_final.data.mapper.toProduct
import com.example.eray_altilar_final.data.remote.ProductService
import com.example.eray_altilar_final.data.remote.dto.ProductResult
import com.example.eray_altilar_final.domain.model.productmodel.Products
import com.example.eray_altilar_final.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class ProductRepositoryImpl @Inject constructor(
    private val productService: ProductService
) : ProductRepository {

    override fun getProducts(limit: Int, skip: Int): Flow<Resource<Products>> = flow {
        emit(Resource.Loading())
        val products = productService.getProductsWithPaging(limit, skip)
        emit(Resource.Success(data = products.toProduct()))
    }
}