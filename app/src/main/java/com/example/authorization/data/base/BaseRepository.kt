package com.example.authorization.data.base

import retrofit2.Response

typealias RepositoryRequest<T> = suspend () -> Response<T>

abstract class BaseRepository {

    suspend fun <T> request(execute: RepositoryRequest<T>): ApiResponse<T> {

        return try {
            val response = execute()
            if (response.isSuccessful) {
                response.body()?.let {
                    ApiResponse.Success(it)
                } ?: ApiResponse.Error(ResponseException("Data not found"))
            } else {
                ApiResponse.Error(ResponseException("Response was ended with error"))
            }

        } catch (e: Throwable) {
            ApiResponse.Error(e)
        }
    }

}