package com.example.eray_altilar_final.data.mapper

import com.example.eray_altilar_final.data.local.entity.FavoritesEntity
import com.example.eray_altilar_final.domain.model.favoritesmodel.Favorites

fun FavoritesEntity.toFavorites(): Favorites {
    return Favorites(
        favoritesId = favoritesId,
        productId = productId,
        name = name,
        thumbnail = thumbnail,
        price = price,
        userId = userId,
    )
}
