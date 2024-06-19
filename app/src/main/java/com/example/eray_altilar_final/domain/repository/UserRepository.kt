package com.example.eray_altilar_final.domain.repository

import com.example.eray_altilar_final.core.Resource
import com.example.eray_altilar_final.domain.model.LoginRequest
import com.example.eray_altilar_final.domain.model.LoginResponse
import com.example.eray_altilar_final.domain.model.usermodel.User
import com.example.eray_altilar_final.domain.model.usermodel.UserUpdateRequest
import com.example.eray_altilar_final.domain.model.usermodel.Users
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUsers(): Flow<Resource<Users>>
    fun getUserById(id: Long): Flow<Resource<User>>
    fun getUserByToken(token: String): Flow<Resource<User>>
    fun filterUsers(key: String, value: String): Flow<Resource<User>>
    fun login(loginInfo: LoginRequest): Flow<Resource<LoginResponse>>
    fun updateUser(id : Long,userUpdateRequest: UserUpdateRequest): Flow<Resource<User>>

}