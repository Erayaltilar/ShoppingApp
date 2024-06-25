package com.example.eray_altilar_final.presentation.ui.favorites

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.eray_altilar_final.R
import com.example.eray_altilar_final.domain.model.favoritesmodel.Favorites
import com.example.eray_altilar_final.presentation.theme.Dimen


@Composable
fun FavoritesScreen(viewModel: FavoritesViewModel = hiltViewModel()) {

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
        FavoritesScreenUI(favorites)
    }
}

@Composable
fun FavoritesScreenUI(favorites: List<Favorites>) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(top = Dimen.spacing_l2)) {
        items(favorites.size) { index ->
            FavoriteItem(favorites.map { it.copy() }[index])
        }
    }
}

@Composable
fun FavoriteItem(favorites: Favorites) {
    Card(
        shape = RoundedCornerShape(Dimen.spacing_s2),
        modifier = Modifier.padding(Dimen.spacing_xs),
        elevation = CardDefaults.elevatedCardElevation(),
    ) {
        Column(modifier = Modifier.padding(Dimen.spacing_m1)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
            ) {
                Image(
                    painter = rememberAsyncImagePainter(favorites.thumbnail),
                    contentDescription = stringResource(R.string.content_description_product_image),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                )
            }

            Spacer(modifier = Modifier.height(Dimen.spacing_xs))

            Text(
                text = favorites.name ?: "",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(Dimen.spacing_xxs))

            Text(
                text = "${favorites.price} USD",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray,
            )

            Spacer(modifier = Modifier.height(Dimen.spacing_xs))
        }
    }
}
