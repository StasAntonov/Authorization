package com.example.authorization.data.repository

import com.example.authorization.data.base.ApiResponse
import com.example.authorization.data.base.BaseRepository
import com.example.authorization.data.remote.QrCodeApi
import com.example.authorization.data.remote.dto.request.toQrCodeRequest
import com.example.authorization.domain.model.QrCode
import com.example.authorization.domain.repository.QrRepository
import javax.inject.Inject

class QrRepositoryImpl @Inject constructor(
    private val api: QrCodeApi
) : BaseRepository(), QrRepository {
    override suspend fun qrCode(body: QrCode): ApiResponse<String> {
        return request { api.qr(body.toQrCodeRequest()) }
    }
}