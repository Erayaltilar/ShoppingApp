package com.example.eray_altilar_final.presentation.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eray_altilar_final.core.Resource
import com.example.eray_altilar_final.domain.model.cartmodel.Cart
import com.example.eray_altilar_final.domain.usecase.product.database.GetProductsInCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    val getProductsInCartUseCase: GetProductsInCartUseCase,
) : ViewModel() {


    private val _cart = MutableStateFlow<Resource<List<Cart>>>(Resource.Loading())
    val cart: StateFlow<Resource<List<Cart>>> get() = _cart

    init {
        getProductsInCart()
    }

    private fun getProductsInCart() {
        viewModelScope.launch {
            getProductsInCartUseCase()
                .collect { result ->
                    _cart.value = result
                }
        }
    }
}
