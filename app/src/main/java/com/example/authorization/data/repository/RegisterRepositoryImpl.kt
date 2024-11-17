package com.example.authorization.data.repository

import com.example.authorization.data.base.ApiResponse
import com.example.authorization.data.base.BaseRepository
import com.example.authorization.data.base.map
import com.example.authorization.data.remote.RegisterApi
import com.example.authorization.data.remote.dto.request.toRegisterUserRequest
import com.example.authorization.domain.model.RegisterUser
import com.example.authorization.domain.repository.RegisterRepository
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val api: RegisterApi
) : BaseRepository(), RegisterRepository {
    override suspend fun register(body: RegisterUser): ApiResponse<Unit> {
        return request { api.register(body.toRegisterUserRequest()) }.map { Unit }
    }

}