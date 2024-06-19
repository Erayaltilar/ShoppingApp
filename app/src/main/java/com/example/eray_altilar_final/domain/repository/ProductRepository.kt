package com.example.eray_altilar_final.domain.repository

import com.example.eray_altilar_final.core.Resource
import com.example.eray_altilar_final.domain.model.productmodel.Category
import com.example.eray_altilar_final.domain.model.productmodel.Product
import com.example.eray_altilar_final.domain.model.productmodel.Products
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getProducts(limit: Int, skip: Int): Flow<Resource<Products>>
    fun getProductsByCategory(category: String): Flow<Resource<Products>>
    fun getCategories(): Flow<Resource<List<Category>>>
}