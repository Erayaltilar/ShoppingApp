package com.example.eray_altilar_final.presentation.ui.cart

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.eray_altilar_final.R
import com.example.eray_altilar_final.core.Resource
import com.example.eray_altilar_final.presentation.theme.Dimen


@Composable
fun CartScreen(viewModel: CartViewModel = hiltViewModel()) {

    val cartState by viewModel.cart.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimen.spacing_l2)
    ) {
        when (val result = cartState) {
            is Resource.Loading -> {
                item {
                    Text("Loading...")
                }
            }

            is Resource.Success -> {
                Log.d("CART SIZE", result.data?.size.toString())
                items(result.data?.size ?: 0) { item ->
                    val product = result.data?.get(item)
                    Column {
                        Image(
                            painter = rememberAsyncImagePainter(product?.thumbnail),
                            contentDescription = stringResource(R.string.cart_product_image),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )

                        Text("Product Name: ${product?.name}")

                        Text("Product Price: ${product?.price}")
                    }
                }
            }

            is Resource.Error -> {
                item {
                    Text("Error: ${result.message}")
                }
            }
        }
    }

}
