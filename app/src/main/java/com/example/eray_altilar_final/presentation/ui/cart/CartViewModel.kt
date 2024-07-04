package com.example.eray_altilar_final.presentation.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eray_altilar_final.core.Resource
import com.example.eray_altilar_final.core.SharedPreferencesManager.getUserId
import com.example.eray_altilar_final.domain.model.cartmodel.Cart
import com.example.eray_altilar_final.domain.model.favoritesmodel.Favorites
import com.example.eray_altilar_final.domain.model.productmodel.Category
import com.example.eray_altilar_final.domain.model.productmodel.Product
import com.example.eray_altilar_final.domain.usecase.product.database.GetFavoritesByUserIdUseCase
import com.example.eray_altilar_final.domain.usecase.product.database.GetProductsInCartByIdUseCase
import com.example.eray_altilar_final.domain.usecase.product.database.GetProductsInCartUseCase
import com.example.eray_altilar_final.domain.usecase.product.database.RemoveProductFromCartUseCase
import com.example.eray_altilar_final.presentation.ui.product.ProductsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    val getProductsInCartByIdUseCase: GetProductsInCartByIdUseCase,
    val removeProductFromCartUseCase: RemoveProductFromCartUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CartScreenUIState())
    val uiState: StateFlow<CartScreenUIState> = _uiState.asStateFlow()

    init {
        getProductsInCart()
    }

    fun removeProductFromCart(productId: Long) {
        removeProductFromCartUseCase(productId).onEach {
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

    private fun getProductsInCart(userId: Long = getUserId()) {
        getProductsInCartByIdUseCase(userId).onEach {
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
                            products = it.data ?: emptyList(),
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

    data class CartScreenUIState(
        val loadingState: Boolean = false,
        val isHaveError: Boolean = false,
        val isSuccess: Boolean = false,
        val errorMessage: String = "",
        val products: List<Cart> = emptyList(),
    )
}
