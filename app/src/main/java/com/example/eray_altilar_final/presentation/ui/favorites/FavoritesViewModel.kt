package com.example.eray_altilar_final.presentation.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eray_altilar_final.core.Resource
import com.example.eray_altilar_final.core.SharedPreferencesManager.getUserId
import com.example.eray_altilar_final.domain.model.favoritesmodel.Favorites
import com.example.eray_altilar_final.domain.usecase.product.database.GetFavoritesByUserIdUseCase
import com.example.eray_altilar_final.domain.usecase.product.database.GetProductsInFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesByUserIdUseCase: GetFavoritesByUserIdUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    init {
        getProductsInFavorites()
    }

    private fun getProductsInFavorites(userId: Long = getUserId()) {
        getFavoritesByUserIdUseCase(userId).onEach {
            when (it) {
                is Resource.Loading -> {
                    _uiState.update { state ->
                        state.copy(loadingState = true)
                    }
                }

                is Resource.Success -> {
                    _uiState.update { state ->
                        state.copy(
                            loadingState = false,
                            favorites = it.data ?: emptyList(),
                            isSuccess = true,
                        )
                    }
                }

                is Resource.Error -> {
                    _uiState.update { state ->
                        state.copy(
                            isHaveError = true,
                            loadingState = false,
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    data class FavoritesUiState(
        val loadingState: Boolean = false,
        val isHaveError: Boolean = false,
        val isSuccess: Boolean = false,
        val errorMessage: String = "",
        val favorites: List<Favorites> = emptyList(),
    )
}