package com.example.eray_altilar_final.data.mapper

import com.example.eray_altilar_final.data.remote.dto.ProductResult
import com.example.eray_altilar_final.data.remote.dto.productdto.ProductDto
import com.example.eray_altilar_final.data.remote.dto.productdto.ReviewDto
import com.example.eray_altilar_final.domain.model.productmodel.Product
import com.example.eray_altilar_final.domain.model.productmodel.Products
import com.example.eray_altilar_final.domain.model.productmodel.Review

fun ProductResult.toProduct(): Products {
    return Products(
        products = products.map { it.toProduct() },
        limit = limit,
        skip = skip,
        total = total,
    )
}

fun ProductDto.toProduct(): Product {
    return Product(
        id = id,
        title = title,
        description = description,
        price = price,
        discountPercentage = discountPercentage,
        rating = rating,
        stock = stock,
        tags = tags,
        brand = brand,
        sku = sku,
        weight = weight,
        warrantyInformation = warrantyInformation,
        shippingInformation = shippingInformation,
        availabilityStatus = availabilityStatus,
        reviews = reviews.map { it.toReview() },
        minimumOrderQuantity = minimumOrderQuantity,
        images = images,
        thumbnail = thumbnail,
    )
}

fun Product.toProductDto() : ProductDto {
    return ProductDto(
        id = id,
        title = title,
        description = description,
        price = price,
        discountPercentage = discountPercentage,
        rating = rating,
        stock = stock,
        tags = tags,
        brand = brand,
        sku = sku,
        weight = weight,
        warrantyInformation = warrantyInformation,
        shippingInformation = shippingInformation,
        availabilityStatus = availabilityStatus,
        reviews = reviews.map { it.toReviewDto() },
        minimumOrderQuantity = minimumOrderQuantity,
        images = images,
        thumbnail = thumbnail,

    )
}

fun Review.toReviewDto() : ReviewDto {
    return ReviewDto(
        rating = rating,
        comment = comment,
        date = date,
        reviewerName = reviewerName,
        reviewerEmail = reviewerEmail,
    )
}

fun ReviewDto.toReview(): Review {
    return Review(
        rating = rating,
        comment = comment,
        date = date,
        reviewerName = reviewerName,
        reviewerEmail = reviewerEmail,
    )
}