package com.example.authorization.domain.repository

import com.example.authorization.data.base.ApiResponse
import com.example.authorization.domain.model.LoginUser

interface LoginRepository {
    suspend fun login(body: LoginUser): ApiResponse<String>
}