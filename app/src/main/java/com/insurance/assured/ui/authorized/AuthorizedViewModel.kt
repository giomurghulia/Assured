package com.insurance.assured.ui.authorized

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class AuthorizedViewModel : ViewModel() {
    private val _action = MutableSharedFlow<Boolean>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val action get() = _action.asSharedFlow()

    private val currentUser get() = Firebase.auth.currentUser

    fun checkUser() {
        if (currentUser == null) {
            _action.tryEmit(false)
        }
    }
}