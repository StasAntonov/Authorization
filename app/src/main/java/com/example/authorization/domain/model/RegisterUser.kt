package com.example.authorization.domain.model

data class RegisterUser(
    val email: String,
    val firstName: String,
    val lastName: String,
    val password: String,
    val phoneNumber: String
)
