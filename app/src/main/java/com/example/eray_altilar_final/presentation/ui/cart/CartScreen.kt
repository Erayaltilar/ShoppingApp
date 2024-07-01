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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.eray_altilar_final.R
import com.example.eray_altilar_final.domain.model.cartmodel.Cart
import com.example.eray_altilar_final.domain.model.productmodel.Product
import com.example.eray_altilar_final.presentation.theme.Dimen

@Composable
fun CartScreen(viewModel: CartViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState.collectAsState()

    with(uiState) {
        if (loadingState) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return@with
        }
        CartScreenUIState(
            products = products,
            removeFromCartClicked = {
                viewModel.removeProductFromCart(it.productId)
            }
        )
    }
}

@Composable
fun CartScreenUIState(
    products: List<Cart>,
    removeFromCartClicked: (Cart) -> Unit = {},
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimen.spacing_l2),
    ) {
        items(products.size) { product ->
            CartItem(
                cartItem = products[product],
                removeFromCartClicked = removeFromCartClicked,
            )
        }

    }
}

@Composable
fun CartItem(
    cartItem: Cart,
    removeFromCartClicked: (Cart) -> Unit = {},
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimen.spacing_s1),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimen.spacing_xs),
        shape = RoundedCornerShape(Dimen.spacing_m1),
    ) {
        Column(modifier = Modifier.padding(Dimen.spacing_m1)) {
            Image(
                painter = rememberAsyncImagePainter(cartItem.thumbnail),
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
                    text = "Product Name: ${cartItem.name}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                )

                Text(
                    text = "Price: ${cartItem.price}₺",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray,
                    fontWeight = FontWeight.SemiBold,
                )
                IconButton(
                    onClick = { removeFromCartClicked(cartItem) },
                    modifier = Modifier.align(Alignment.CenterVertically),
                    content = { Icon(painter = painterResource(id = R.drawable.ic_shopping_cart), contentDescription = "Remove from cart") },
                )
            }
        }
    }
}