package com.example.eray_altilar_final.domain.usecase.product.database

import com.example.eray_altilar_final.core.Resource
import com.example.eray_altilar_final.domain.model.favoritesmodel.Favorites
import com.example.eray_altilar_final.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoveProductFromFavorites @Inject constructor(private val repository: DatabaseRepository) {
    operator fun invoke(productId: Long): Flow<Resource<Favorites>> {
        return repository.removeProductFromFavorites(productId)
    }
}