package com.insurance.assured.common.resource

sealed class Resource<T> {
    data class Success<T>(val model: T) : Resource<T>()
    data class Error<T>(val message: String, val placeholder: T? = null) : Resource<T>()
}

val <T>Resource<T>.data get() = (this as? Resource.Success)?.model