package com.example.authorization.data.repository

import com.example.authorization.data.base.ApiResponse
import com.example.authorization.data.base.BaseRepository
import com.example.authorization.data.remote.LoginApi
import com.example.authorization.data.remote.dto.request.toLoginUserRequest
import com.example.authorization.domain.model.LoginUser
import com.example.authorization.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val api: LoginApi
) : BaseRepository(), LoginRepository {
    override suspend fun login(body: LoginUser): ApiResponse<String> {
        return request { api.login(body.toLoginUserRequest()) }
    }
}