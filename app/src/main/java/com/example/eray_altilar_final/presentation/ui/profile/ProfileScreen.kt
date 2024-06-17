package com.example.eray_altilar_final.presentation.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ProfileScreen() {
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
