package com.example.authorization.domain.repository

import com.example.authorization.data.base.ApiResponse
import com.example.authorization.domain.model.QrCode

interface QrRepository {

    suspend fun qrCode(body: QrCode): ApiResponse<String>
}