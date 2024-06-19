package com.example.eray_altilar_final.data.mapper

import com.example.eray_altilar_final.data.remote.dto.UserResult
import com.example.eray_altilar_final.data.remote.dto.userdto.AddressDto
import com.example.eray_altilar_final.data.remote.dto.userdto.UserDto
import com.example.eray_altilar_final.data.remote.dto.userdto.UserUpdateRequestDto
import com.example.eray_altilar_final.domain.model.usermodel.Address
import com.example.eray_altilar_final.domain.model.usermodel.User
import com.example.eray_altilar_final.domain.model.usermodel.UserUpdateRequest
import com.example.eray_altilar_final.domain.model.usermodel.Users

fun UserResult.toUsers(): Users {
    return Users(
        users = users.map { it.toUser() },
        limit = limit,
        skip = skip,
        total = total,
    )
}

fun UserDto.toUser(): User {
    return User(
        id = id,
        firstName = firstName,
        lastName = lastName,
        age = age,
        gender = gender,
        email = email,
        phone = phone,
        username = username,
        password = password,
        birthDate = birthDate,
        image = image,
        bloodGroup = bloodGroup,
        height = height,
        weight = weight,
        address = address?.toAddress(),
    )
}

fun AddressDto.toAddress(): Address {
    return Address(
        address = address,
        city = city,
        state = state,
        country = country,
        stateCode = stateCode,
        postalCode = postalCode,
    )
}

fun UserUpdateRequestDto.toUserUpdateRequest(): UserUpdateRequest {
    return UserUpdateRequest(
        firstName = firstName,
        lastName = lastName,
        email = email,
    )
}
fun UserUpdateRequest.toUserUpdateRequestDto(): UserUpdateRequestDto {
    return UserUpdateRequestDto(
        firstName = firstName,
        lastName = lastName,
        email = email,
    )
}





