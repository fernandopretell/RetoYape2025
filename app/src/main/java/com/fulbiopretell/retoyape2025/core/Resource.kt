package com.fulbiopretell.retoyape2025.core

sealed class Resource<out T> {
    data object Loading : Resource<Nothing>()
    data object Idle : Resource<Nothing>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<Nothing>(val message: String) : Resource<Nothing>()
}
