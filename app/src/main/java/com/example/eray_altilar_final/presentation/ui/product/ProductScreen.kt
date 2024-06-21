package com.example.eray_altilar_final.presentation.ui.product

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.eray_altilar_final.R
import com.example.eray_altilar_final.core.SharedPreferencesManager.getUserId
import com.example.eray_altilar_final.domain.model.cartmodel.Cart
import com.example.eray_altilar_final.domain.model.productmodel.Category
import com.example.eray_altilar_final.domain.model.productmodel.Product
import com.example.eray_altilar_final.presentation.theme.Dimen
import com.example.eray_altilar_final.presentation.theme.FF2196F3
import com.example.eray_altilar_final.presentation.theme.FF21CBF3

@Composable
fun ProductScreen(
    viewModel: ProductsViewModel = hiltViewModel(),
) {
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
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }

        if (isSuccessForCategory) {
            /* sonar - comment */
        }

        if (isSuccessForGetProducts) {
            /* sonar - comment */
        }

        if (isSuccessAddToCart) {
            Toast.makeText(context, stringResource(R.string.toast_text_product_added_to_cart), Toast.LENGTH_LONG).show()
        }
        if (isLiked) {
            Toast.makeText(context, "Liked", Toast.LENGTH_LONG).show()
        }

        ProductScreenUI(
            categories = categories,
            onCategoryClick = {
                viewModel.loadProductsByCategory(it)
            },
            products = products,
            onAddToCartClicked = {
                viewModel.addProductToCart(
                    getUserId(),
                    it.id ?: 0,
                    it.title ?: "",
                    it.price ?: 0.0,
                    it.thumbnail ?: "",
                )
            },
            addFavoriteClicked = {
                viewModel.addProductInFavorites(productId = it.id ?: -1, userId = getUserId())
                Log.d("ProductAddFavoriteClicked", "addFavoriteClicked: $it")
                Log.d("UserIDAddFavoriteClicked", "addFavoriteClicked: ${getUserId()}")

            },
        )
    }
}

@Composable
fun ProductScreenUI(
    categories: List<Category>,
    onCategoryClick: (String) -> Unit,
    products: List<Product>,
    onAddToCartClicked: (Product) -> Unit,
    addFavoriteClicked: (Product) -> Unit,
) {
    LazyColumn( modifier = Modifier.padding(top = Dimen.spacing_xs)) {
        item {
            CategoryList(categories) {
                onCategoryClick(it)
            }
        }
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimen.spacing_xs, 100.dp, Dimen.spacing_xs, Dimen.spacing_xs),
    ) {
        items(products.size) { productCount ->
            ProductItem(
                product = products[productCount],
                onAddToCart = onAddToCartClicked,
                addFavoriteClicked = addFavoriteClicked,
            )
        }
    }
}

@Composable
fun CategoryList(categories: List<Category>, onCategoryClick: (String) -> Unit) {
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = Dimen.spacing_l)
            .horizontalScroll(scrollState),
    ) {
        categories.forEach { category ->
            Card(
                shape = RoundedCornerShape(Dimen.spacing_s2),
                modifier = Modifier
                    .padding(Dimen.spacing_xs)
                    .clickable { onCategoryClick(category.name ?: "") },
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            Brush.horizontalGradient(colors = listOf(Color.FF2196F3, Color.FF21CBF3)),
                        )
                        .padding(Dimen.spacing_xs),
                ) {
                    Text(
                        text = category.name ?: "",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                    )
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: Product, addFavoriteClicked: (Product) -> Unit, onAddToCart: (Product) -> Unit) {

    Card(
        shape = RoundedCornerShape(Dimen.spacing_s2),
        modifier = Modifier.padding(Dimen.spacing_xs),
        elevation = CardDefaults.elevatedCardElevation(),
    ) {
        Box {
            Column(modifier = Modifier.padding(Dimen.spacing_m1)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(product.thumbnail),
                        contentDescription = stringResource(R.string.content_description_product_image),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                    )
                    IconButton(
                        onClick = { addFavoriteClicked(product) },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(Dimen.spacing_xs)
                            .background(Color.White, shape = RoundedCornerShape(50)),
                    ) {
                        Icon(
                            painter = rememberAsyncImagePainter(R.drawable.ic_heart),
                            contentDescription = stringResource(R.string.contentDescriptionFavorite),
                            tint = Color.Red,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(Dimen.spacing_xs))
                Text(
                    text = product.title ?: "",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(Dimen.spacing_xxs))
                Text(
                    text = "${product.price} USD",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray,
                )
                Spacer(modifier = Modifier.height(Dimen.spacing_xs))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        onClick = { onAddToCart(product) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                        ),
                        contentPadding = PaddingValues(),
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(Color.FF2196F3, Color.FF21CBF3),
                                    ),
                                    shape = RoundedCornerShape(50),
                                )
                                .padding(horizontal = Dimen.spacing_m1, vertical = Dimen.spacing_xs),
                        ) {
                            Icon(
                                painter = rememberAsyncImagePainter(R.drawable.ic_shopping_cart),
                                contentDescription = stringResource(R.string.add_to_cart),
                                tint = Color.White,
                            )
                        }
                    }
                }
            }
        }
    }
}
