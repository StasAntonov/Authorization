package com.example.authorization.data.remote.dto.request

import com.example.authorization.domain.model.QrCode

data class QrCodeRequest(
    val qrCodeInfo: String
)

fun QrCode.toQrCodeRequest() = QrCodeRequest(
    qrCodeInfo = qrCodeInfo
)