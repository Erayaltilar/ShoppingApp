import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.eray_altilar_final.R
import com.example.eray_altilar_final.domain.model.productmodel.Category
import com.example.eray_altilar_final.domain.model.productmodel.Product
import com.example.eray_altilar_final.presentation.ui.product.ProductsViewModel

@Composable
fun ProductScreen(
    viewModel: ProductsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    with(uiState) {
        if (loadingState) {
            CircularProgressIndicator()
            return@with
        }

        if (isHaveError) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }

        if (isSuccessForCategory) {
            LazyColumn(
            ) {
                item {
                    CategoryList(categories) {
                        Log.d("Category", "ProductScreen: $it")
                        viewModel.loadProductsByCategory(it)
                    }
                }
            }
        }
        if (isSuccessForGetProducts) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp, 100.dp, 16.dp, 0.dp)
            ) {
                items(products.size) { productCount ->
                    ProductItem(product = products[productCount], onAddToCart = { selectedProduct ->
                        Toast.makeText(context, "${selectedProduct.title} sepete eklendi", Toast.LENGTH_SHORT).show()
                    })
                }
            }
        }
    }
}


@Composable
fun CategoryList(categories: List<Category>, onCategoryClick: (String) -> Unit) {
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .horizontalScroll(scrollState)
    ) {
        categories.forEach { category ->
            Card(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = category.name ?: "",
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { onCategoryClick(category.name ?: "") },
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}

@Composable
fun ProductItem(product: Product, onAddToCart: (Product) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onAddToCart(product) }, elevation = CardDefaults.elevatedCardElevation()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(product.thumbnail),
                contentDescription = "Product Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.title!!, style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${product.price} USD", style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /* handle favorite click */ }) {
                    Icon(
                        painter = rememberAsyncImagePainter(R.drawable.ic_heart), contentDescription = "Favorite"
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                IconButton(onClick = { onAddToCart(product) }) {
                    Icon(
                        painter = rememberAsyncImagePainter(R.drawable.ic_shopping_cart), contentDescription = "Add to Cart"
                    )
                }
            }
        }
    }
}
