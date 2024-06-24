package com.example.eray_altilar_final.domain.usecase.notification

import com.example.eray_altilar_final.domain.repository.FirebaseMessageRepository
import javax.inject.Inject


class OnMessageReceivedUseCase @Inject constructor(
    private val repository: FirebaseMessageRepository,
) {
    suspend operator fun invoke(from: String?, messageId: String?, data: Map<String, String>, body: String?) {
        repository.onMessageReceived(from, messageId, data, body)
    }
}