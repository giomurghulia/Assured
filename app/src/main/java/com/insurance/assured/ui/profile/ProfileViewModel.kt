package com.insurance.assured.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insurance.assured.data.local.AppConfigDataStore
import com.insurance.assured.domain.usecases.policyusecases.GetUserDataUseCase
import com.insurance.assured.domain.usecases.policyusecases.GetUserPoliciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val appConfigDataStore: AppConfigDataStore,
    private val userPoliciesUseCase: GetUserPoliciesUseCase,
    private val userDataUseCase: GetUserDataUseCase
) : ViewModel() {


    fun setPassCode(passcode: String) {
        viewModelScope.launch {
            appConfigDataStore.setPassCode(passcode)
        }
    }

    fun longOut(){
        userDataUseCase.clearUserData()
    }
}