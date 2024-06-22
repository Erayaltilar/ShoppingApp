package com.example.eray_altilar_final.data.repository

import com.example.eray_altilar_final.core.Resource
import com.example.eray_altilar_final.data.mapper.toCategory
import com.example.eray_altilar_final.data.mapper.toProduct
import com.example.eray_altilar_final.data.remote.ProductService
import com.example.eray_altilar_final.domain.model.productmodel.Category
import com.example.eray_altilar_final.domain.model.productmodel.Product
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

    override fun getProductsByCategory(category: String): Flow<Resource<Products>> = flow {
        emit(Resource.Loading())
        val product = productService.getProductsByCategory(category).toProduct()
        emit(Resource.Success(data = product))
    }

    override fun getCategories(): Flow<Resource<List<Category>>> = flow {
        emit(Resource.Loading())
        val categories = productService.getCategories().map { it.toCategory() }
        emit(Resource.Success(data = categories))
    }

    override fun searchProduct(query: String): Flow<Resource<Products>> = flow {
        emit(Resource.Loading())
        val products = productService.searchProducts(query).toProduct()
        emit(Resource.Success(data = products))
    }
}