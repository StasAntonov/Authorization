package com.example.authorization.data.remote.dto.request

import com.example.authorization.domain.model.RegisterUser

data class RegisterUserRequest(
    val email: String,
    val firstName: String,
    val lastName: String,
    val password: String,
    val phoneNumber: String
)

fun RegisterUser.toRegisterUserRequest() = RegisterUserRequest(
    email = email,
    firstName = firstName,
    lastName = lastName,
    password = password,
    phoneNumber = phoneNumber
)
