package com.insurance.assured.ui.viewstate

data class ViewState<T: Any>(val data: T? = null, val error: String? = null, val load: Boolean = false)

