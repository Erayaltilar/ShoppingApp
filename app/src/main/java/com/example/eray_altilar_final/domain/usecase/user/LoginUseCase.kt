package com.example.eray_altilar_final.domain.usecase.user

import com.example.eray_altilar_final.core.Resource
import com.example.eray_altilar_final.domain.model.loginmodel.LoginRequest
import com.example.eray_altilar_final.domain.model.loginmodel.LoginResponse
import com.example.eray_altilar_final.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val userRepository: UserRepository) {

    operator fun invoke(loginInfo: LoginRequest): Flow<Resource<LoginResponse>> {
        return userRepository.login(loginInfo)
    }
}