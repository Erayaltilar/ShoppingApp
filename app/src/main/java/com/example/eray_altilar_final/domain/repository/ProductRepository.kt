package com.example.eray_altilar_final.domain.repository

import com.example.eray_altilar_final.core.Resource
import com.example.eray_altilar_final.domain.model.productmodel.Products
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getProducts(limit: Int, skip: Int): Flow<Resource<Products>>
}