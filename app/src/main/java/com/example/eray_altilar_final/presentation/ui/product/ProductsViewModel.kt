package com.example.eray_altilar_final.presentation.ui.product

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eray_altilar_final.core.Resource
import com.example.eray_altilar_final.core.SharedPreferencesManager.getUserId
import com.example.eray_altilar_final.domain.model.favoritesmodel.Favorites
import com.example.eray_altilar_final.domain.model.productmodel.Category
import com.example.eray_altilar_final.domain.model.productmodel.Product
import com.example.eray_altilar_final.domain.usecase.product.database.AddProductInFavoritesUseCase
import com.example.eray_altilar_final.domain.usecase.product.database.AddProductToCartUseCase
import com.example.eray_altilar_final.domain.usecase.product.database.GetFavoritesByUserIdUseCase
import com.example.eray_altilar_final.domain.usecase.product.database.GetProductsInFavoritesUseCase
import com.example.eray_altilar_final.domain.usecase.product.database.RemoveProductFromFavorites
import com.example.eray_altilar_final.domain.usecase.product.remote.AddProductToCartFromApi
import com.example.eray_altilar_final.domain.usecase.product.remote.GetCategoriesUseCase
import com.example.eray_altilar_final.domain.usecase.product.remote.GetProductsByCategoryUseCase
import com.example.eray_altilar_final.domain.usecase.product.remote.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getProductsByCategoryUseCase: GetProductsByCategoryUseCase,
    private val getFavoritesByUserIdUseCase: GetFavoritesByUserIdUseCase,
    private val addProductInFavoritesUseCase: AddProductInFavoritesUseCase,
    private val addProductToCartFromApi: AddProductToCartFromApi,
    private val removeProductFromFavorites: RemoveProductFromFavorites,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductScreenUIState())
    val uiState: StateFlow<ProductScreenUIState> = _uiState.asStateFlow()

    private var currentPage = 0
    private val pageSize = 30

    init {
        val userId = getUserId()
        getProductsInFavorites()
        loadProducts()
        loadCategories()
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

    fun loadProductsByCategory(category: String) {
        getProductsByCategoryUseCase(category).onEach {
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
                            products = it.data?.products ?: emptyList(),
                        )
                    }
                }

                is Resource.Error -> {
                    _uiState.update { state ->
                        state.copy(
                            loadingState = false,
                            isHaveError = true,
                            errorMessage = it.errorMessage.orEmpty(),
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

    private fun loadProducts() {
        getProductsUseCase(pageSize, currentPage * pageSize).onEach {
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
                            isSuccessForGetProducts = true,
                            products = it.data?.products ?: emptyList(),
                        )
                    }
                }

                is Resource.Error -> {
                    _uiState.update { state ->
                        state.copy(
                            loadingState = false,
                            isHaveError = true,
                            errorMessage = it.errorMessage.orEmpty(),
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun loadCategories() {
        getCategoriesUseCase().onEach {
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
                            isSuccessForCategory = true,
                            categories = it.data ?: emptyList(),
                        )
                    }
                }

                is Resource.Error -> {
                    _uiState.update { state ->
                        state.copy(
                            loadingState = false,
                            isHaveError = true,
                            errorMessage = it.errorMessage.orEmpty(),
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
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

    data class ProductScreenUIState(
        val loadingState: Boolean = false,
        val isHaveError: Boolean = false,
        val isLikeSuccess: Boolean = false,
        val isDislikeSuccess: Boolean = false,
        val isSuccessAddToCart: Boolean = false,
        val isSuccess: Boolean = false,
        val isSuccessForGetProducts: Boolean = false,
        val isSuccessForCategory: Boolean = false,
        val errorMessage: String = "",
        val products: List<Product> = emptyList(),
        val product: Product? = null,
        val categories: List<Category> = emptyList(),
        val selectedCategory: String = "",
        val productsInFavorites: List<Favorites> = emptyList(),
    )
}
