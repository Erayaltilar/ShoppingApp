package com.example.eray_altilar_final.domain.repository

interface FirebaseMessageRepository {
    suspend fun onNewToken(token: String)
    suspend fun onMessageReceived(from: String?, messageId: String?, data: Map<String, String>, body: String?)
}