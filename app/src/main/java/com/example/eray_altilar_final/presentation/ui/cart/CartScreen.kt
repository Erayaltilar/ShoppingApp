package com.example.eray_altilar_final.presentation.ui.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
            .padding(Dimen.spacing_l2),
    ) {
        when (val result = cartState) {
            is Resource.Loading -> {
                item {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Loading...", style = MaterialTheme.typography.titleMedium)
                    }
                }
            }

            is Resource.Success -> {
                items(result.data ?: emptyList()) { product ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = Dimen.spacing_s1),
                        elevation = CardDefaults.cardElevation(defaultElevation = Dimen.spacing_xs),
                        shape = RoundedCornerShape(Dimen.spacing_m1),
                    ) {
                        Column(modifier = Modifier.padding(Dimen.spacing_m1)) {
                            Image(
                                painter = rememberAsyncImagePainter(product.thumbnail),
                                contentDescription = stringResource(R.string.cart_product_image),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .clip(RoundedCornerShape(Dimen.spacing_m1)),
                            )

                            Spacer(modifier = Modifier.height(Dimen.spacing_s1))

                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Text(
                                    text = "Product Name: ${product.name}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(1f),
                                )
                                Text(
                                    text = "Price: ${product.price}₺",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.Gray,
                                    fontWeight = FontWeight.SemiBold,
                                )
                            }
                        }
                    }
                }
            }

            is Resource.Error -> {
                item {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Error: ${result.message}", style = MaterialTheme.typography.titleMedium, color = Color.Red)
                    }
                }
            }
        }
    }
}
