package com.example.authorization.data.remote

import com.example.authorization.data.remote.dto.request.LoginUserRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("login")
    suspend fun login(@Body body: LoginUserRequest): Response<String>
}