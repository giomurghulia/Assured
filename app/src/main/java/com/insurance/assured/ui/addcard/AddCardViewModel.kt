package com.insurance.assured.ui.addcard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insurance.assured.domain.usecases.CleanCash
import com.insurance.assured.domain.usecases.card.DeleteUserCardUseCase
import com.insurance.assured.domain.usecases.card.GetUserCardUseCase
import com.insurance.assured.domain.usecases.card.InsertUserCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCardViewModel @Inject constructor(
    private val insertUserCardUseCase: InsertUserCardUseCase
) : ViewModel() {
    private val result = MutableSharedFlow<Boolean>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    private val _state = MutableStateFlow<Boolean?>(null)
    val state get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            result.collect{

            }
        }
    }
    fun insertCard(userId: String, cardToken: String, cardNum: String) {
        viewModelScope.launch {
            _state.tryEmit(insertUserCardUseCase.invoke(userId, cardToken, cardNum))
        }
    }
}