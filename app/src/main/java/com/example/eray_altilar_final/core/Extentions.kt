package com.example.eray_altilar_final.core

import android.app.Activity
import android.content.Intent


fun Activity.goToTheActivity(activityToGo: Activity, isFinish: Boolean = false) {
    val intent = Intent(this, activityToGo::class.java)
    startActivity(intent)
    if (isFinish) finish()
}
