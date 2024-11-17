package com.example.authorization.data.remote.dto.request

import com.example.authorization.domain.model.LoginUser

data class LoginUserRequest(
    val email: String?,
    val password: String,
    val phoneNumber: String?
)

fun LoginUser.toLoginUserRequest() = LoginUserRequest(
    email = email,
    password = password,
    phoneNumber = phoneNumber
)