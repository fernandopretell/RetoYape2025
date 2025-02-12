package com.fulbiopretell.retoyape2025.data.models.response

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponseDto(
    val traceId: String? = null,
    val errorCode: String? = null,
    val systemMessage: String,
    val userMessage: String? = null,
)
