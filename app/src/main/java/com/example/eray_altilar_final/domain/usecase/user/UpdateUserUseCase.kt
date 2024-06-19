package com.example.eray_altilar_final.domain.usecase.user

import com.example.eray_altilar_final.core.Resource
import com.example.eray_altilar_final.domain.model.usermodel.User
import com.example.eray_altilar_final.domain.model.usermodel.UserUpdateRequest
import com.example.eray_altilar_final.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(private val userRepository: UserRepository) {

    operator fun invoke(id: Long, userUpdateRequest: UserUpdateRequest): Flow<Resource<User>> {
        return userRepository.updateUser(id, userUpdateRequest)
    }
}