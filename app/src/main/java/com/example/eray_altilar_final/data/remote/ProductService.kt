package com.example.eray_altilar_final.data.remote

import com.example.eray_altilar_final.data.remote.dto.ProductResult
import com.example.eray_altilar_final.data.remote.dto.productdto.CategoryDto
import com.example.eray_altilar_final.data.remote.dto.productdto.ProductDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {

    @GET("products/categories")
    suspend fun getCategories(): List<CategoryDto>

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category: String): ProductResult

    @GET("products/{id}")
    suspend fun getProductById(
        @Path("id") id: Long
    ): ProductResult

    @GET("products")
    suspend fun getProductsWithPaging(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): ProductResult

    @GET("products/search")
    suspend fun searchProducts(@Query("q") query: String): ProductResult
}