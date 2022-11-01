package com.insurance.assured.ui.policy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insurance.assured.common.extensions.toResult
import com.insurance.assured.common.resource.Result
import com.insurance.assured.domain.models.user_policy.UserPolicyModel
import com.insurance.assured.domain.usecases.policyusecases.GetPolicyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PolicyViewModel @Inject constructor(
    private val getPolicyUseCase: GetPolicyUseCase,
) : ViewModel() {

    private val _state = MutableSharedFlow<Result<List<UserPolicyModel>>>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val state get() = _state.asSharedFlow()

    init {

    }

    fun getPolicy() {
        viewModelScope.launch {
            getPolicyUseCase.invoke().toResult().collect {
                when (it) {
                    is Result.Success -> {
                        _state.tryEmit(Result.Success(it.data))
                    }
                    is Result.Loading -> {
                        _state.tryEmit(Result.Loading)
                    }
                    is Result.Error -> {
                        _state.tryEmit(Result.Error(it.throwable))
                    }
                }
            }
        }
    }
}