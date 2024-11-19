package com.example.authorization.data.remote

import com.example.authorization.data.remote.dto.request.QrCodeRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface QrCodeApi {

    @POST("qr-code")
    suspend fun qr(@Body body: QrCodeRequest): Response<String>
}