package com.example.eray_altilar_final.presentation.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.eray_altilar_final.core.goToTheActivity
import com.example.eray_altilar_final.presentation.theme.ErayAltilarFinalTheme
import com.example.eray_altilar_final.presentation.ui.login.LoginActivity
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
