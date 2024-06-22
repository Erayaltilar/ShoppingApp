package com.example.eray_altilar_final.presentation.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.eray_altilar_final.R
import com.example.eray_altilar_final.core.goToTheActivity
import com.example.eray_altilar_final.presentation.theme.ErayAltilarFinalTheme
import com.example.eray_altilar_final.presentation.ui.login.LoginActivity
import com.example.eray_altilar_final.presentation.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ErayAltilarFinalTheme {
                SplashScreen()
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            this.goToTheActivity(activityToGo = LoginActivity(), isFinish = true)
        }, 3000)
    }
}
