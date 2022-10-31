package com.insurance.assured

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val throwable: Throwable) : Result<Nothing>()
}


fun <T> Result<T>.onSuccess(callback: (data: T) -> Unit) {
    if (this is Result.Success) {
        callback(data)
    }
}

fun Result<*>.onError(callback: (error: Throwable) -> Unit) {
    if (this is Result.Error){
        callback(throwable)
    }
}

fun Result<*>.onLoading(callback: () -> Unit) {
    if (this is Result.Loading){
        callback()
    }
}