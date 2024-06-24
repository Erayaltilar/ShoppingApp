package com.example.eray_altilar_final.domain.usecase.notification

import com.example.eray_altilar_final.domain.repository.FirebaseMessageRepository
import javax.inject.Inject

class OnNewTokenUseCase @Inject constructor(
    private val repository: FirebaseMessageRepository,
) {
    suspend operator fun invoke(token: String) {
        repository.onNewToken(token)
    }
}