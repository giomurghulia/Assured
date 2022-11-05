package com.insurance.assured.ui.policyitem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insurance.assured.common.extensions.toResult
import com.insurance.assured.common.resource.Result
import com.insurance.assured.domain.models.userpolicy.UserPolicyModel
import com.insurance.assured.domain.usecases.policyusecases.GetUserPoliciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PolicyItemViewModel @Inject constructor(
    private val getUserPoliciesUseCase: GetUserPoliciesUseCase,
) : ViewModel() {

    private val _state = MutableSharedFlow<Result<UserPolicyModel>>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val state get() = _state.asSharedFlow()


    fun getItemData(itemId:String){
        viewModelScope.launch {
            getUserPoliciesUseCase.getPolicyById(itemId).toResult().collect{
                when (it) {
                    is Result.Success -> {
                        _state.tryEmit(it)
                    }
                    is Result.Loading -> {}
                    is Result.Error -> {}
                }
            }
        }
    }
}