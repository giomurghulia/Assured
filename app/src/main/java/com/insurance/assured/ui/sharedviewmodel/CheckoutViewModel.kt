package com.insurance.assured.ui.sharedviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.insurance.assured.domain.usecases.checkoutsusecase.CheckoutUseCase
import com.insurance.assured.ui.mappers.toDomainModel
import com.insurance.assured.ui.presentationmodels.planlist.PlanListItemModel
import com.insurance.assured.ui.presentationmodels.sharedmodel.CheckoutModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(private val checkoutUseCase: CheckoutUseCase) :
    ViewModel() {
    private val _checkoutState: MutableStateFlow<CheckoutModel> = MutableStateFlow(CheckoutModel())
    val checkOutState = _checkoutState.asStateFlow()
    private val _purchaseSuccessSharedFlow = MutableSharedFlow<Boolean>()
    val purchaseSuccessSharedFlow = _purchaseSuccessSharedFlow.asSharedFlow()
    fun onItemChoose(plan: PlanListItemModel) {
        viewModelScope.launch {
            _checkoutState.value = _checkoutState.value.copy(insurancePacket = plan)
        }
    }

    fun onUserInfoInserted(userId: String, idList: List<String>) {
        viewModelScope.launch {
            _checkoutState.value = _checkoutState.value.copy(userId = userId, idList = idList)
            checkoutUseCase.insertUnfinishedCheckoutUseCase(_checkoutState.value.toDomainModel())
        }
    }

    fun onCheckout(token: String) {
        viewModelScope.launch {
            Firebase.auth.currentUser?.let {
                _purchaseSuccessSharedFlow.emit(
                    checkoutUseCase.insertUserPurchasedItemsUseCase(
                        _checkoutState.value.toDomainModel(),
                        it.email!!
                    )
                )
                return@launch
            }
            _purchaseSuccessSharedFlow.emit(false)
        }
    }

    fun deleteUnfinishedCheckout(id: Int) {
        viewModelScope.launch {
            checkoutUseCase.deleteCheckoutUseCase(id)
        }
    }
}