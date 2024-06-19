package com.example.eray_altilar_final.presentation.ui.product

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eray_altilar_final.core.Resource
import com.example.eray_altilar_final.core.SharedPreferencesManager.getToken
import com.example.eray_altilar_final.domain.model.productmodel.Category
import com.example.eray_altilar_final.domain.model.productmodel.Product
import com.example.eray_altilar_final.domain.model.productmodel.Products
import com.example.eray_altilar_final.domain.usecase.product.database.AddProductToCartUseCase
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getProductsByCategoryUseCase: GetProductsByCategoryUseCase


) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchScreenUIState())
    val uiState: StateFlow<SearchScreenUIState> = _uiState.asStateFlow()

    private var currentPage = 0
    private val pageSize = 10


    init {
        loadProducts()
        loadCategories()
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

    fun addProductToCart(userId: Long, product: Product) {
        viewModelScope.launch {
            addProductToCartUseCase(
                userId = userId,
                productId = product.id!!,
                name = product.title!!,
                price = product.price!!,
                thumbnail = product.thumbnail.orEmpty(),
            ).collect { result ->
                if (result is Resource.Success) {
                    Log.d("productViewModel", "addProductToCart: ${result.data}")
                }
                if (result is Resource.Error) {
                    Log.d("productViewModel", "addProductToCart: ${result.errorMessage}")
                }
            }
        }
    }

    data class SearchScreenUIState(
        val loadingState: Boolean = false,
        val isHaveError: Boolean = false,
        val isSuccess: Boolean = false,
        val isSuccessForGetProducts: Boolean = false,
        val isSuccessForCategory: Boolean = false,
        val errorMessage: String = "",
        val products: List<Product> = emptyList(),
        val categories: List<Category> = emptyList(),
        val selectedCategory: String = ""
    )
}
