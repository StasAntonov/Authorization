package com.example.authorization.data.base

sealed class ApiResponse<T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error<T>(val throwable: Throwable) : ApiResponse<T>()
}

fun <IN, OUT> ApiResponse<IN>.map(transform: (IN) -> OUT): ApiResponse<OUT> {
    return when (this) {
        is ApiResponse.Error -> ApiResponse.Error(throwable = this.throwable)
        is ApiResponse.Success -> ApiResponse.Success(data = transform.invoke(this.data))
    }
}


class ResponseException(
    override val message: String
) : Throwable()

data class ResponseError(
    val message: String,
    val status: Int,
    val timestamp: String
)

