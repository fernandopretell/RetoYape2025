package com.fulbiopretell.retoyape2025.core

import com.fulbiopretell.retoyape2025.data.models.response.ErrorResponseDto
import com.fulbiopretell.retoyape2025.data.network.NetConstants.UNEXPECTED_ERROR
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

sealed class Failure {
    data class SocketTimeOutFailure(val message: String? = null) : Failure()
    data class IOFailure(val message: String? = null) : Failure()
    data class HttpFailure(val code: Int, val message: String?) : Failure()
    data class UnexpectedFailure(val message: String? = null) : Failure()
}

fun Exception.toFailure(): Failure = when (this) {
    is SocketTimeoutException -> Failure.SocketTimeOutFailure(message = message)
    is IOException -> Failure.IOFailure(message = message)
    is HttpException -> {
        val responseError = convertErrorBody(this)
        Failure.HttpFailure(
            code = code(),
            message = responseError?.systemMessage ?: message
        )
    }

    else -> Failure.UnexpectedFailure(message = message)
}

fun Failure.toMessage(): String = when (this) {
    is Failure.HttpFailure -> message
    is Failure.IOFailure -> message
    is Failure.SocketTimeOutFailure -> message
    is Failure.UnexpectedFailure -> message
} ?: UNEXPECTED_ERROR


private fun convertErrorBody(throwable: HttpException): ErrorResponseDto? {
    return try {
        throwable.response()?.errorBody()?.source()?.let {
            Json {
                ignoreUnknownKeys = true
                explicitNulls = false
            }.decodeFromString<ErrorResponseDto>(it.readUtf8())
        }
    } catch (exception: Exception) {
        null
    }
}