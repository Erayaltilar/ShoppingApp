package com.example.eray_altilar_final.data.repository

import com.example.eray_altilar_final.core.Resource
import com.example.eray_altilar_final.data.mapper.toLoginRequestDto
import com.example.eray_altilar_final.data.mapper.toLoginResponse
import com.example.eray_altilar_final.data.mapper.toUser
import com.example.eray_altilar_final.data.mapper.toUserUpdateRequestDto
import com.example.eray_altilar_final.data.mapper.toUsers
import com.example.eray_altilar_final.data.remote.UserApi
import com.example.eray_altilar_final.domain.model.loginmodel.LoginRequest
import com.example.eray_altilar_final.domain.model.loginmodel.LoginResponse
import com.example.eray_altilar_final.domain.model.usermodel.User
import com.example.eray_altilar_final.domain.model.usermodel.UserUpdateRequest
import com.example.eray_altilar_final.domain.model.usermodel.Users
import com.example.eray_altilar_final.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi,
) : UserRepository {

    override fun getUsers(): Flow<Resource<Users>> = flow {
        emit(Resource.Loading())
        val data = userApi.getUsers()
        emit(Resource.Success(data.toUsers()))
    }

    override fun getUserById(id: Long): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        val response = userApi.getUserById(id = id)
        emit(Resource.Success(data = response.toUser()))
    }

    override fun getUserByToken(token: String): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        try {
            val response = userApi.getUserByToken(token = token)
            emit(Resource.Success(data = response.toUser()))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage.orEmpty()))
        }
    }

    override fun filterUsers(key: String, value: String): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        val response = userApi.filterUsers(key = key, value = value)
        emit(Resource.Success(data = response.toUser()))
    }

    override fun login(loginInfo: LoginRequest): Flow<Resource<LoginResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = userApi.login(loginInfo.toLoginRequestDto())
            emit(Resource.Success(data = response.toLoginResponse()))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage.orEmpty()))
        }
    }

    override fun updateUser(id: Long, userUpdateRequest: UserUpdateRequest): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        val response = userApi.updateUser(id, userUpdateRequest.toUserUpdateRequestDto())
        emit(Resource.Success(data = response.toUser()))

    }
}
