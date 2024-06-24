package com.example.eray_altilar_final.domain.usecase.remote_config

import com.example.eray_altilar_final.core.Resource
import com.example.eray_altilar_final.domain.repository.RemoteConfigRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRemoteConfigValueUseCase @Inject constructor(
    private val repository: RemoteConfigRepository
) {
    operator fun invoke(key: String): Flow<Resource<String>> {
        return repository.getConfigValue(key)
    }
}