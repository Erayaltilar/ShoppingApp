import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.eray_altilar_final.R
import com.example.eray_altilar_final.core.Resource
import com.example.eray_altilar_final.domain.model.productmodel.Product
import com.example.eray_altilar_final.presentation.ui.product.ProductsViewModel

@Composable
fun ProductScreen(
    userId: Long,
    viewModel: ProductsViewModel = hiltViewModel()
) {
    val productsState by viewModel.products.collectAsState()

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        when (val result = productsState) {
            is Resource.Loading -> {
                item {
                    // Display a loading indicator
                    Text("Loading...")
                }
            }
            is Resource.Success -> {
                result.data?.products?.size?.let { product ->
                    items(product) {count ->
                        ProductItem(
                            product = result.data.products[count],
                            onAddToCart = { viewModel.addProductToCart(userId, it) }
                        )
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


@Composable
fun ProductItem(product: Product, onAddToCart: (Product) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onAddToCart(product) },
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(product.thumbnail),
                contentDescription = "Product Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.title,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${product.price} USD",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /* handle favorite click */ }) {
                    Icon(
                        painter = rememberAsyncImagePainter(R.drawable.ic_heart),
                        contentDescription = "Favorite"
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                IconButton(onClick = { onAddToCart(product) }) {
                    Icon(
                        painter = rememberAsyncImagePainter(R.drawable.ic_shopping_cart),
                        contentDescription = "Add to Cart"
                    )
                }
            }
        }
    }
}
