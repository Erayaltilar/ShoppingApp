package com.example.eray_altilar_final.presentation.ui.search

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.eray_altilar_final.domain.model.productmodel.Product
import com.example.eray_altilar_final.presentation.theme.Dimen
import com.example.eray_altilar_final.presentation.ui.product.ProductItem

@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    with(uiState) {
        if (loadingState) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return@with
        }
        if (isHaveError) {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
            return@with
        }
        if (isSuccess) {
        }

        SearchScreenUI(
            products = products,
            searchProduct = {
                viewModel.searchProduct(it)
            }
        )
    }
}

@Composable
fun SearchScreenUI(
    products: List<Product>,
    searchProduct: (String) -> Unit
) {
    var query by remember { mutableStateOf("") }
    val filteredProducts = products.filter {
        it.title?.contains(query, ignoreCase = true) == true
    }

    Column {
        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                searchProduct(it)
            },
            label = { Text("Search") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimen.spacing_m1, top = Dimen.spacing_l)
        )
        LazyColumn {
            items(filteredProducts.size) { productIndex ->
                val product = filteredProducts[productIndex]
                ProductItem(
                    product = product,
                )
            }
        }
    }
}