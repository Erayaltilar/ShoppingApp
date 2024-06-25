package com.example.eray_altilar_final.presentation.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eray_altilar_final.core.Resource
import com.example.eray_altilar_final.domain.model.favoritesmodel.Favorites
import com.example.eray_altilar_final.domain.model.productmodel.Product
import com.example.eray_altilar_final.domain.usecase.product.database.AddProductInFavoritesUseCase
import com.example.eray_altilar_final.domain.usecase.product.database.AddProductToCartUseCase
import com.example.eray_altilar_final.domain.usecase.product.database.GetProductsInFavoritesUseCase
import com.example.eray_altilar_final.domain.usecase.product.database.RemoveProductFromFavorites
import com.example.eray_altilar_final.domain.usecase.product.remote.AddProductToCartFromApi
import com.example.eray_altilar_final.domain.usecase.product.remote.SearchProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    val searchProductUseCase: SearchProductUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val addProductToCartFromApi: AddProductToCartFromApi,
    private val getProductsInFavoritesUseCase: GetProductsInFavoritesUseCase,
    private val addProductInFavoritesUseCase: AddProductInFavoritesUseCase,
    private val removeProductFromFavorites: RemoveProductFromFavorites,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchScreenUIState())
    val uiState: StateFlow<SearchScreenUIState> = _uiState.asStateFlow()

    init {
        searchProduct("")
        getProductsInFavorites()
    }

    fun addProductInFavorites(userId: Long, productId: Long, name: String, price: Double, thumbnail: String) {
        addProductInFavoritesUseCase(userId, productId, name, price, thumbnail).onEach {
            when (it) {
                is Resource.Loading -> {}

                is Resource.Success -> {
                    _uiState.update { state ->
                        state.copy(
                            isLikeSuccess = true,
                        )
                    }
                }

                is Resource.Error -> {
                    Log.d("ErrorProductViewModel", "addProductInFavorites: ${it.errorMessage}")

                }
            }
        }.launchIn(viewModelScope)
    }

    fun removeFromFavorites(productId: Long) {
        removeProductFromFavorites(productId).onEach {
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
                            isDislikeSuccess = true,
                        )
                    }
                }

                is Resource.Error -> {
                    _uiState.update { state ->
                        state.copy(
                            loadingState = false,
                            isHaveError = true,
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun addProductToCart(userId: Long, productId: Long, name: String, price: Double, thumbnail: String) {
        addProductToCartUseCase(userId, productId, name, price, thumbnail).onEach {
            when (it) {
                is Resource.Loading -> {}

                is Resource.Success -> {
                    _uiState.update { state ->
                        state.copy(
                            isSuccessAddToCart = true,
                        )
                    }
                    Log.d("TAG", "addProductToCart: ${it.data}")
                }

                is Resource.Error -> {
                    Log.d("TAG", "addProductToCart: ${it.errorMessage}")
                }
            }
        }.launchIn(viewModelScope)
    }

    // dummyjson api'si değişmeyeceğinden işe yaramıyor bunun için üstteki room'u kullanıyoruz
    fun addProductToCartApi(productId: Long) {
        addProductToCartFromApi(productId = productId).onEach {
            when (it) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    _uiState.update { state ->
                        state.copy(
                            isSuccessAddToCart = true,
                            product = it.data,
                        )
                    }
                }

                is Resource.Error -> {
                    Log.d("TAG", "addProductToCart: ${it.errorMessage}")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun searchProduct(query: String) {
        searchProductUseCase(query).onEach {
            when (it) {
                is Resource.Loading -> {}

                is Resource.Success -> {
                    _uiState.update { state ->
                        state.copy(
                            loadingState = false,
                            isHaveError = false,
                            isSuccess = true,
                            products = it.data?.products ?: emptyList(),
                        )
                    }
                }

                is Resource.Error -> {
                    _uiState.update { state ->
                        state.copy(
                            loadingState = false,
                            isHaveError = true,
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getProductsInFavorites() {
        getProductsInFavoritesUseCase().onEach {
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
                            isSuccess = true,
                            productsInFavorites = it.data ?: emptyList(),
                        )
                    }
                }

                is Resource.Error -> {
                    _uiState.update { state ->
                        state.copy(
                            loadingState = false,
                            isHaveError = true,
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    data class SearchScreenUIState(
        val loadingState: Boolean = false,
        val isHaveError: Boolean = false,
        val isSuccess: Boolean = false,
        val errorMessage: String = "",
        val products: List<Product> = emptyList(),
        val productsInFavorites: List<Favorites> = emptyList(),
        val isSuccessAddToCart: Boolean = false,
        val product: Product? = null,
        val isLikeSuccess: Boolean = false,
        val isDislikeSuccess: Boolean = false,
    )
}