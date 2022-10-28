package com.insurance.assured.ui.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.insurance.assured.di.datastore.AppConfigDataStore
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val appConfigDataStore: AppConfigDataStore
) : ViewModel() {

    private val _action = MutableSharedFlow<Boolean>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val action get() = _action.asSharedFlow()

    private val currentUser = Firebase.auth.currentUser

    private var passCode: String? = null

    init {

    }


    fun checkUser() {
        viewModelScope.launch {
            passCode = appConfigDataStore.getPassCode()

            delay(2000)
            if (passCode.isNullOrEmpty() && currentUser != null) {
                _action.tryEmit(true)
            } else {
                _action.tryEmit(true)
            }

        }

    }

}