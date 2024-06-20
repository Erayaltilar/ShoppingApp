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
import com.example.eray_altilar_final.domain.model.usermodel.UserUpdateRequest
import com.example.eray_altilar_final.presentation.theme.Dimen
import com.example.eray_altilar_final.presentation.theme.FF2196F3
import com.example.eray_altilar_final.presentation.theme.FF21CBF3
import com.example.eray_altilar_final.presentation.theme.FFF44336

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel()) {

    val state by viewModel.user.collectAsState()

    val userUpdateRequest = UserUpdateRequest(
        email = "eray@example.com",
        firstName = "Eray",
        lastName = "Altilar",
    )

    when (state) {
        is Resource.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is Resource.Success -> {
            state.data?.let {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.FF2196F3,
                                    Color.FF21CBF3,
                                ),
                            ),
                        )
                        .padding(Dimen.spacing_m1),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Dimen.spacing_s2),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Card(
                            shape = RoundedCornerShape(Dimen.spacing_m1),
                            elevation = CardDefaults.elevatedCardElevation(Dimen.spacing_xxs),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(Dimen.spacing_m1),
                        ) {
                            Column(
                                modifier = Modifier
                                    .background(Color.White)
                                    .padding(Dimen.spacing_l),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(it.image),
                                    contentDescription = stringResource(R.string.profil_resmi),
                                    modifier = Modifier
                                        .size(120.dp)
                                        .clip(CircleShape),
                                )
                                Spacer(modifier = Modifier.height(Dimen.spacing_m1))
                                Text(
                                    text = it.username,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = Dimen.font_size_l,
                                    modifier = Modifier.padding(Dimen.spacing_xs),
                                )
                                Text(text = it.email, fontSize = Dimen.font_size_18, modifier = Modifier.padding(4.dp))
                                Text(text = it.firstName, fontSize = Dimen.font_size_18, modifier = Modifier.padding(4.dp))
                                Text(text = it.lastName, fontSize = Dimen.font_size_18, modifier = Modifier.padding(4.dp))

                                Spacer(modifier = Modifier.height(Dimen.spacing_m1))

                                Button(
                                    onClick = {
                                        viewModel.updateUser(it.id, userUpdateRequest)
                                    },
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
                                            .padding(horizontal = 16.dp, vertical = 8.dp),
                                    ) {
                                        Text(
                                            text = stringResource(R.string.profili_guncelle),
                                            color = Color.White,
                                            fontSize = 18.sp,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        is Resource.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = state.message.orEmpty(), color = Color.Red, fontSize = 18.sp)
            }
        }
    }
}