package com.example.authorization.data.base

sealed class ApiResponse<T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error<T>(val throwable: Throwable) : ApiResponse<T>()
}

class ResponseException(
    override val message: String
) : Throwable()


