package com.example.eray_altilar_final.domain.usecase

import com.example.eray_altilar_final.core.Resource
import com.example.eray_altilar_final.domain.model.usermodel.User
import com.example.eray_altilar_final.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(private val userRepository: UserRepository) {

    operator fun invoke(userId: Long): Flow<Resource<User>> {
        return userRepository.getUserById(userId)
    }
}