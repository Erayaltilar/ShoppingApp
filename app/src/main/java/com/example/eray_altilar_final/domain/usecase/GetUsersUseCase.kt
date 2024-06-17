package com.example.eray_altilar_final.domain.usecase

import com.example.eray_altilar_final.core.Resource
import com.example.eray_altilar_final.domain.model.usermodel.Users
import com.example.eray_altilar_final.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val userRepository: UserRepository) {

    operator fun invoke(): Flow<Resource<Users>> {
        return userRepository.getUsers()
    }
}