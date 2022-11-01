package com.insurance.assured.ui.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.insurance.assured.data.local.AppConfigDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val appConfigDataStore: AppConfigDataStore
) : ViewModel() {

    private val _action = MutableSharedFlow<Boolean?>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val action get() = _action.asSharedFlow()

    private val currentUser get() = Firebase.auth.currentUser


    private var passCode: String? = null

    init {
    }


    fun checkUser() {
        viewModelScope.launch {
            appConfigDataStore.setPassCode("")
            passCode = appConfigDataStore.getPassCode()

            if (currentUser == null) {
                _action.tryEmit(null)
            } else if (!passCode.isNullOrEmpty()) {
                _action.tryEmit(false)
            } else {
                _action.tryEmit(true)
            }
        }


    }

}