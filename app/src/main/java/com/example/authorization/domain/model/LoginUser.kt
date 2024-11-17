package com.example.authorization.domain.model

data class LoginUser(
    val email: String? = null,
    val password: String,
    val phoneNumber: String? = null
)