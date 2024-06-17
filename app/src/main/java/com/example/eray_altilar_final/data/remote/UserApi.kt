package com.example.eray_altilar_final.data.remote

import com.example.eray_altilar_final.data.remote.dto.LoginRequestDto
import com.example.eray_altilar_final.data.remote.dto.LoginResponseDto
import com.example.eray_altilar_final.data.remote.dto.UserResult
import com.example.eray_altilar_final.data.remote.dto.userdto.UserDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequestDto): LoginResponseDto

    @GET("users")
    suspend fun getUsers(): UserResult

    @GET("users/filter")
    suspend fun filterUsers(
        @Query("key") key: String,
        @Query("value") value: String
    ): UserDto
}