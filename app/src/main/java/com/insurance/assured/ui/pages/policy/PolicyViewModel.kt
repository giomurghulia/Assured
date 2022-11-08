package com.insurance.assured.ui.pages.policy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.insurance.assured.common.extensions.toResult
import com.insurance.assured.common.resource.Result
import com.insurance.assured.domain.usecases.policyusecases.GetUserDataUseCase
import com.insurance.assured.domain.usecases.policyusecases.GetUserPoliciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PolicyViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val getUserPoliciesUseCase: GetUserPoliciesUseCase,
    private val policyPageListBuilder: PolicyPageListBuilder
) : ViewModel() {
    private val currentUser get() = Firebase.auth.currentUser

    private val _payload = MutableStateFlow(PolicyPagePayload())

    private val _state = MutableStateFlow<List<PolicyListItem>>(listOf())
    val state get() = _state.asStateFlow()

    private val refreshData = MutableSharedFlow<Boolean>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val refreshUserData = MutableSharedFlow<Boolean>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val refreshUserPolicies = MutableSharedFlow<Boolean>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    init {
        viewModelScope.launch {
            merge(
                refreshData,
                refreshUserData
            ).flatMapLatest { refresh ->
                if (checkUser()) {
                    getUserDataUseCase.invoke(refresh).toResult()
                } else {
                    flow { emit(Result.Loading) }
                }
            }.collectLatest {
                _payload.value = _payload.value.copy(userData = it)
            }
        }

        viewModelScope.launch {
            merge(
                refreshData,
                refreshUserPolicies
            ).flatMapLatest { refresh ->
                if (checkUser()) {
                    getUserPoliciesUseCase.invoke(refresh).toResult()
                } else {
                    flow { emit(Result.Loading) }
                }
            }.collectLatest {
                _payload.value = _payload.value.copy(userPolicies = it)
            }
        }

        viewModelScope.launch {
            _payload.collectLatest { data ->

                if (checkUser()) {
                    _state.value = policyPageListBuilder.buildList(data)
                } else {
                    _state.value = getNonUserData()
                }

            }
        }
    }

    private fun checkUser(): Boolean {
        return currentUser != null
    }

    private fun getNonUserData(): List<PolicyListItem> {
        return listOf(PolicyListItem.NoUserItem)
    }

    fun getUserData() {
        refreshData.tryEmit(false)
    }

    fun refresh() {
        refreshData.tryEmit(true)
    }

    fun refreshUserData() {
        refreshUserData.tryEmit(true)
    }

    fun refreshUserPolicies() {
        refreshUserPolicies.tryEmit(true)
    }

}

