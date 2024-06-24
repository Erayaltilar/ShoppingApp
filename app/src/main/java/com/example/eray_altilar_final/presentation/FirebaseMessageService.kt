package com.example.eray_altilar_final.presentation

import com.example.eray_altilar_final.domain.usecase.notification.OnMessageReceivedUseCase
import com.example.eray_altilar_final.domain.usecase.notification.OnNewTokenUseCase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MyFirebaseMessageService : FirebaseMessagingService() {

    @Inject
    lateinit var onNewTokenUseCase: OnNewTokenUseCase

    @Inject
    lateinit var onMessageReceivedUseCase: OnMessageReceivedUseCase

    override fun onNewToken(token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            onNewTokenUseCase(token)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        CoroutineScope(Dispatchers.IO).launch {
            onMessageReceivedUseCase(
                message.from,
                message.messageId,
                message.data,
                message.notification?.body,
            )
        }
    }
}