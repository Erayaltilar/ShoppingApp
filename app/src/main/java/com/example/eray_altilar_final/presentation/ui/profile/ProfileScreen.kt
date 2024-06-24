package com.example.eray_altilar_final.presentation.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.eray_altilar_final.R
import com.example.eray_altilar_final.core.Resource
import com.example.eray_altilar_final.domain.model.productmodel.Product
import com.example.eray_altilar_final.domain.model.usermodel.User
import com.example.eray_altilar_final.domain.model.usermodel.UserUpdateRequest
import com.example.eray_altilar_final.presentation.theme.Dimen
import com.example.eray_altilar_final.presentation.theme.FF2196F3
import com.example.eray_altilar_final.presentation.theme.FF21CBF3
import com.example.eray_altilar_final.presentation.theme.FFF44336

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState.collectAsState()

    val userUpdateRequest = UserUpdateRequest(
        email = "eray@example.com",
        firstName = "Eray",
        lastName = "Altilar",
        username = "ErayA"
    )

    with(uiState) {
        if (loadingState) (
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                })
        if (isHaveError) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = stringResource(R.string.user_error))
            }
        }
        ProfileScreenUI(
            user = user,
            updateUserOnClick = { viewModel.updateUser(user?.id ?: 0, userUpdateRequest) },
        )

    }
}

@Composable
fun ProfileScreenUI(
    user: User?,
    updateUserOnClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = Dimen.spacing_l2)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.FF2196F3,
                        Color.FF21CBF3,
                    ),
                ),
            ),
        contentAlignment = Alignment.Center,
    ) {
        Card(
            shape = RoundedCornerShape(Dimen.spacing_m1),
            elevation = CardDefaults.elevatedCardElevation(Dimen.spacing_xxs),
            modifier = Modifier
                .padding(Dimen.spacing_m1),
        ) {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .padding(Dimen.spacing_l),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = rememberAsyncImagePainter(user?.image ?: ""),
                    contentDescription = stringResource(R.string.profil_resmi),
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),
                )

                Spacer(modifier = Modifier.height(Dimen.spacing_m1))

                Text(
                    text = user?.username ?: "",
                    fontWeight = FontWeight.Bold,
                    fontSize = Dimen.font_size_l,
                    modifier = Modifier.padding(Dimen.spacing_xs),
                )

                Text(text = user?.email ?: "", fontSize = Dimen.font_size_18, modifier = Modifier.padding(Dimen.spacing_xxs))

                Text(text = user?.firstName ?: "", fontSize = Dimen.font_size_18, modifier = Modifier.padding(Dimen.spacing_xxs))

                Text(text = user?.lastName ?: "", fontSize = Dimen.font_size_18, modifier = Modifier.padding(Dimen.spacing_xxs))

                Spacer(modifier = Modifier.height(Dimen.spacing_m1))

                Button(
                    onClick = updateUserOnClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.FFF44336,
                        contentColor = Color.White,
                    ),
                    contentPadding = PaddingValues(),
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        Color.FF2196F3,
                                        Color.FF21CBF3,
                                    )
                                ),
                                shape = RoundedCornerShape(50)
                            )
                            .padding(horizontal = Dimen.spacing_m1, vertical = Dimen.spacing_xs),
                    ) {
                        Text(
                            text = stringResource(R.string.profili_guncelle),
                            color = Color.White,
                            fontSize = Dimen.font_size_18,
                        )
                    }
                }
            }
        }
    }
}
