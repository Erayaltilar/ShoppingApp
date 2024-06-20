package com.example.eray_altilar_final.data.mapper

import com.example.eray_altilar_final.data.local.entity.CartEntity
import com.example.eray_altilar_final.domain.model.cartmodel.Cart
import com.example.eray_altilar_final.domain.model.productmodel.Product

fun CartEntity.toCart(): Cart {
    return Cart(
        cartId = cartId,
        productId = productId,
        name = name,
        thumbnail = thumbnail,
        price = price,
        userId = userId
    )
}

fun Cart.toCartEntity(): CartEntity {
    return CartEntity(
        cartId = cartId,
        productId = productId,
        name = name,
        thumbnail = thumbnail,
        price = price,
        userId = userId
    )
}