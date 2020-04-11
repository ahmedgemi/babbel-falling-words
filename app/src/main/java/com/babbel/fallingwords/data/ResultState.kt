package com.babbel.fallingwords.data

sealed class ResultState<out R> {

    data class Success<out T>(val data: T) : ResultState<T>()
    data class Error(val exception: Exception) : ResultState<Nothing>()
    object Loading : ResultState<Nothing>()
}