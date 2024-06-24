package com.example.eray_altilar_final.data.repository

import android.util.Log
import com.example.eray_altilar_final.domain.repository.FirebaseMessageRepository
import javax.inject.Inject

class FirebaseMessageRepositoryImpl @Inject constructor() : FirebaseMessageRepository {

    override suspend fun onNewToken(token: String) {
        Log.d("newToken", token)
    }

    override suspend fun onMessageReceived(from: String?, messageId: String?, data: Map<String, String>, body: String?) {
        Log.d("Message from", "$from")
        Log.d("Message messageId", "$messageId")
        Log.d("Message data", "$data")
        body?.let {
            Log.d("Message body", it)
        }
    }
}