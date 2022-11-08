package com.insurance.assured.ui.pages.signIn

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class SignInViewModel : ViewModel() {
    private val _state = MutableSharedFlow<Resource>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val state get() = _state.asSharedFlow()


    fun login(task: com.google.android.gms.tasks.Task<AuthResult>) {
        if (task.isSuccessful) {
            // Sign in success, update UI with the signed-in user's information
            Log.d(ContentValues.TAG, "signInWithEmail:success")

            _state.tryEmit(Resource.Success(Unit))
        } else {
            // If sign in fails, display a message to the user.
            Log.w(ContentValues.TAG, "signInWithEmail:failure", task.exception)

            try {
                throw task.exception!!
            } catch (e: Exception) {
                _state.tryEmit(Resource.Error(e.message!!))
            }
        }
    }

    fun register(task: com.google.android.gms.tasks.Task<AuthResult>) {
        if (task.isSuccessful) {
            // Sign in success, update UI with the signed-in user's information

            Log.d(ContentValues.TAG, "createUserWithEmail:success")
            _state.tryEmit(Resource.Success(Unit))
        } else {
            // If sign in fails, display a message to the user.
            Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)

            try {
                throw task.exception!!
            } catch (e: Exception) {
                _state.tryEmit(Resource.Error(e.message!!))
            }
        }
    }

}