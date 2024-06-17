package com.example.eray_altilar_final.presentation.ui.product

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eray_altilar_final.core.Resource
import com.example.eray_altilar_final.domain.model.cartmodel.Cart
import com.example.eray_altilar_final.domain.model.productmodel.Product
import com.example.eray_altilar_final.domain.model.productmodel.Products
import com.example.eray_altilar_final.domain.usecase.AddProductToCartUseCase
import com.example.eray_altilar_final.domain.usecase.GetProductsInCartUseCase
import com.example.eray_altilar_final.domain.usecase.GetProductsUseCase
import com.example.eray_altilar_final.domain.usecase.RemoveProductFromCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase
) : ViewModel() {

    private val _products = MutableStateFlow<Resource<Products>>(Resource.Loading())
    val products: StateFlow<Resource<Products>> get() = _products

    private var currentPage = 0
    private val pageSize = 30

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            getProductsUseCase(pageSize, currentPage * pageSize)
                .collect { result ->
                    _products.value = result
                    if (result is Resource.Success && result.data != null) {
                        currentPage++
                    }
                }
        }
    }

    fun addProductToCart(userId: Long, product: Product) {
        viewModelScope.launch {
            addProductToCartUseCase(
                userId = userId,
                productId = product.id,
                name = product.title,
                price = product.price,
                thumbnail = product.thumbnail
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
}

//@HiltViewModel
//class ProductsViewModel @Inject constructor(
//    private val getProductsUseCase: GetProductsUseCase
//) : ViewModel() {
//
//    private val _products = MutableStateFlow<Resource<Products>>(Resource.Loading())
//    val products: StateFlow<Resource<Products>> get() = _products
//
//    private var currentPage = 0
//    private val pageSize = 10
//
//    init {
//        loadProducts()
//    }
//
//    fun loadProducts() {
//        viewModelScope.launch {
//            getProductsUseCase(pageSize, currentPage * pageSize)
//                .collect { result ->
//                    _products.value = result
//                    if (result is Resource.Success && result.data != null) {
//                        currentPage++
//                    }
//                }
//        }
//    }
//}