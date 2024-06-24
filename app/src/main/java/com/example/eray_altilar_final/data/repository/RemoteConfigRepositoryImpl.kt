package com.example.eray_altilar_final.data.repository

import com.example.eray_altilar_final.core.Resource
import com.example.eray_altilar_final.domain.repository.RemoteConfigRepository
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteConfigRepositoryImpl @Inject constructor(private val remoteConfig: FirebaseRemoteConfig) : RemoteConfigRepository {

    override fun fetchAndActivate(): Flow<Resource<Boolean>> = flow {
        var result = false
        try {
            emit(Resource.Loading())
            remoteConfig.fetchAndActivate().addOnSuccessListener {
                result = true
            }
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage.orEmpty()))
        }
    }

    override fun getConfigValue(key: String): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Success(remoteConfig.getString(key)))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage.orEmpty()))
        }
    }
}