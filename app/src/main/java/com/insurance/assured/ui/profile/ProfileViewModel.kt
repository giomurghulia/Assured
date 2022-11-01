package com.insurance.assured.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insurance.assured.data.local.AppConfigDataStore
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val appConfigDataStore: AppConfigDataStore
) : ViewModel() {


    fun setPassCode(passcode: String) {
        viewModelScope.launch {
            appConfigDataStore.setPassCode(passcode)
        }
    }
}