package com.example.authorization.data.remote

import com.example.authorization.data.remote.dto.request.RegisterUserRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApi {

    @POST("register")
    suspend fun register(@Body body: RegisterUserRequest): Response<Unit>

}