package com.example.eray_altilar_final.data.remote.dto.productdto

data class ProductDto (
    val id: Long?,
    val title: String?,
    val description: String?,
    val price: Double?,
    val discountPercentage: Double?,
    val rating: Double?,
    val stock: Long?,
    val tags: List<String>?,
    val brand: String? = null,
    val sku: String?,
    val weight: Long?,
    val warrantyInformation: String?,
    val shippingInformation: String?,
    val availabilityStatus: Boolean?,
    val reviews: List<ReviewDto>?,
    val minimumOrderQuantity: Long?,
    val images: List<String>?,
    val thumbnail: String?,
)
