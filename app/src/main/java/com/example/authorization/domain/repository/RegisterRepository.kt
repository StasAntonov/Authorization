package com.example.authorization.domain.repository

import com.example.authorization.data.base.ApiResponse
import com.example.authorization.domain.model.RegisterUser

interface RegisterRepository {
    suspend fun register(body: RegisterUser): ApiResponse<Unit>
}