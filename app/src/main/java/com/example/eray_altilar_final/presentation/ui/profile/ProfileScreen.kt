package com.example.eray_altilar_final.presentation.ui.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.eray_altilar_final.core.Resource
import com.example.eray_altilar_final.domain.model.usermodel.UserUpdateRequest

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel()) {

    val state by viewModel.user.collectAsState()

    val userUpdateRequest = UserUpdateRequest(
        email = "ErayFALAN MAIL",
        firstName = "ERAY",
        lastName = "ALTILAR",
    )

    when (state) {
        is Resource.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is Resource.Success -> {
            state.data?.let {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                ) {
                    LazyColumn {
                        item {
                            LazyRow {
                                item {
                                    Text(text = it.username)
                                }
                            }
                            Text(text = it.email)
                            Text(text = it.firstName)
                            Text(text = it.lastName)

                            Button(onClick = {
                                viewModel.updateUser(it.id, userUpdateRequest)
                            }) {
                                Text(text = "Update")
                            }
                        }
                    }
                }
            }

        }

        is Resource.Error -> {
            Text(text = state.message.orEmpty())
        }
    }
}
