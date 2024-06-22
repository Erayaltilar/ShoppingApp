package com.example.eray_altilar_final.presentation.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eray_altilar_final.core.Resource
import com.example.eray_altilar_final.domain.model.productmodel.Product
import com.example.eray_altilar_final.domain.usecase.product.remote.GetProductsUseCase
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
    val getProductsUseCase: GetProductsUseCase,
    val searchProductUseCase: SearchProductUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchScreenUIState())
    val uiState: StateFlow<SearchScreenUIState> = _uiState.asStateFlow()

    private var currentPage = 0
    private val pageSize = 30

    init {
         searchProduct( "")
    }

    private fun getProducts() {
        getProductsUseCase(pageSize, currentPage).onEach {
            when (it) {
                is Resource.Loading -> {
                    _uiState.update { state ->
                        state.copy(
                            loadingState = true,
                            isHaveError = false,
                            isSuccess = false,
                        )
                    }
                }

                is Resource.Success -> {
                    _uiState.update { state ->
                        state.copy(
                            loadingState = false,
                            isHaveError = false,
                            isSuccess = true,
                            errorMessage = "",
                            products = it.data?.products ?: emptyList(),
                        )
                    }
                }

                is Resource.Error -> {
                    _uiState.update { state ->
                        state.copy(
                            loadingState = false,
                            isHaveError = true,
                            isSuccess = false,
                        )
                    }
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

    data class SearchScreenUIState(
        val loadingState: Boolean = false,
        val isHaveError: Boolean = false,
        val isSuccess: Boolean = false,
        val errorMessage: String = "",
        val products: List<Product> = emptyList(),
    )
}