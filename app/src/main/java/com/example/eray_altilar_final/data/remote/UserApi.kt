package com.example.eray_altilar_final.data.remote

import com.example.eray_altilar_final.data.remote.dto.UserResult
import com.example.eray_altilar_final.data.remote.dto.logindto.LoginRequestDto
import com.example.eray_altilar_final.data.remote.dto.logindto.LoginResponseDto
import com.example.eray_altilar_final.data.remote.dto.userdto.UserDto
import com.example.eray_altilar_final.data.remote.dto.userdto.UserUpdateRequestDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequestDto): LoginResponseDto

    @PATCH("users/{id}")
    suspend fun updateUser(@Path("id") id: Long, @Body request: UserUpdateRequestDto): UserDto

    @GET("users")
    suspend fun getUsers(): UserResult

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: Long): UserDto

    @GET("users/filter")
    suspend fun filterUsers(
        @Query("key") key: String,
        @Query("value") value: String
    ): UserDto
}