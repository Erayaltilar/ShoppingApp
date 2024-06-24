package com.example.eray_altilar_final.presentation.ui.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.eray_altilar_final.R
import com.example.eray_altilar_final.presentation.theme.Dimen
import com.example.eray_altilar_final.presentation.theme.FF21CBF3

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigate: () -> Unit = { /* sonar - comment */ },
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
            return@with
        }

        if (isLoginSuccess) {
            navigate()
        }

        if (isFetchSuccessful) {
            viewModel.getRemoteConfig("backgroundColor")
        }

        LoginScreenUI(
            backgroundColor = backgroundColor,
            onLoginButtonClicked = {
                viewModel.getToken("michaelw", "michaelwpass")
            },
        )
    }
}

@Composable
fun LoginScreenUI(
    backgroundColor: String,
    onLoginButtonClicked: () -> Unit,
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(android.graphics.Color.parseColor(backgroundColor)))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimen.spacing_l2),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_shoping_app),
                contentDescription = stringResource(R.string.app_logo),
                modifier = Modifier.size(128.dp),
            )
            Spacer(modifier = Modifier.height(Dimen.spacing_m1))
            Text(
                text = stringResource(R.string.shopping_app),
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                modifier = Modifier.padding(bottom = Dimen.spacing_l2),
            )
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text(stringResource(R.string.username)) },
                leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(Dimen.spacing_xs))
                    .padding(bottom = Dimen.spacing_m1),
            )
            Spacer(modifier = Modifier.height(Dimen.spacing_m1))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(stringResource(R.string.password)) },
                leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(Dimen.spacing_xs)),
            )
            Spacer(modifier = Modifier.height(Dimen.spacing_m1))
            Button(
                onClick = onLoginButtonClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(Dimen.spacing_xs)),
            ) {
                Text(
                    text = stringResource(R.string.login),
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.FF21CBF3,
                )
            }
        }
    }
}
