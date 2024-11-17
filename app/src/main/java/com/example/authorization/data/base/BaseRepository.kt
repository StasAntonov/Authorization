package com.example.authorization.data.base

import com.google.gson.Gson
import retrofit2.Response
import java.net.UnknownHostException

typealias RepositoryRequest<T> = suspend () -> Response<T>

abstract class BaseRepository {

    private val gson: Gson = Gson()

    suspend fun <T> request(execute: RepositoryRequest<T>): ApiResponse<T> {
        return try {
            val response = execute()
            when (response.code()) {
                SUCCESS_CODE -> {
                    val body = response.body()
                    if (body != null) {
                        ApiResponse.Success(body)
                    } else {
                        val rawResponse = response.errorBody()?.string() ?: null
                        if (!rawResponse.isNullOrEmpty()) {
                            ApiResponse.Success(rawResponse as T)
                        } else {
                            ApiResponse.Success(Unit as T)
                        }
                    }
                }

                ERROR_CODE -> {
                    val errorData =
                        gson.fromJson(response.errorBody()?.charStream(), ResponseError::class.java)
                    ApiResponse.Error(ResponseException(errorData.message))
                }

                else -> ApiResponse.Error(UnknownHostException())
            }
        } catch (e: Throwable) {
            ApiResponse.Error(e)
        }
    }

    private companion object {
        const val SUCCESS_CODE = 200
        const val ERROR_CODE = 400
    }
}