package com.example.eray_altilar_final.data.remote

import com.example.eray_altilar_final.data.remote.dto.ProductResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {

    @GET("products/{id}")
    suspend fun getProductById(
        @Path("id") id: Long
    ): ProductResult

    @GET("products")
    suspend fun getProductsWithPaging(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): ProductResult
}