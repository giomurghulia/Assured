package com.insurance.assured.ui.vuewstate

data class ViewState<T: Any>(val data: T? = null, val error: String? = null, val load: Boolean = false)

