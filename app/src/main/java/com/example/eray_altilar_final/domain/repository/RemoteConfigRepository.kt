package com.example.eray_altilar_final.domain.repository

import com.example.eray_altilar_final.core.Resource
import kotlinx.coroutines.flow.Flow

interface RemoteConfigRepository {
    fun fetchAndActivate(): Flow<Resource<Boolean>>
    fun getConfigValue(key: String): Flow<Resource<String>>
}