package com.insurance.assured

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

fun <T : Any> Flow<T>.toResult(): Flow<Result<T>> =
    this.map { result ->
        Result.Success(result)
    }.catch<Result<T>> { e ->
        emit(Result.Error(e))
    }.onStart { emit(Result.Loading) }