package com.insurance.assured.ui.pages.checkout.lastcheckout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.insurance.assured.domain.usecases.card.GetUserCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LastCheckoutViewModel @Inject constructor(private val useCase: GetUserCardUseCase) :
    ViewModel() {

    private val _cardsStateFlow = MutableStateFlow<List<AdapterCardModel>>(listOf())
    val cardStateFlow = _cardsStateFlow.asStateFlow()


    fun getCards() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase(Firebase.auth.currentUser?.email ?: "").collect {
                _cardsStateFlow.value = it.map { AdapterCardModel(it) }
            }
        }
    }

    fun onCheck(card: AdapterCardModel) {
        viewModelScope.launch(Dispatchers.Default) {
            _cardsStateFlow.value = _cardsStateFlow.value.map {
                if (it == card) {
                    it.copy(isChecked = true)
                } else {
                    it.copy(isChecked = false)
                }
            }
        }
    }
}