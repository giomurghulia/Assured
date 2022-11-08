package com.insurance.assured.ui.pages.signIn

sealed class Resource {
    data class Success(val unit: Unit) : Resource()
    data class Error(val errorMessage: String) : Resource()
}