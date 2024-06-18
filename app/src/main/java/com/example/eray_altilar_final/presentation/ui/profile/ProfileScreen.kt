package com.example.eray_altilar_final.presentation.ui.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.eray_altilar_final.core.Resource

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel()) {

    val state by viewModel.user.collectAsState()

    when (state) {
        is Resource.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is Resource.Success -> {
            state.data?.let { Text(text = it.email) }

        }

        is Resource.Error -> {
            Text(text = state.message.orEmpty())
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            item {
                LazyRow {
                    item {
                        Text(text = "Eray's")
                        Text(text = "Falann")
                    }
                }
            }
        }
    }
}
