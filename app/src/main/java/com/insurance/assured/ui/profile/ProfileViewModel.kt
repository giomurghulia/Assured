package com.insurance.assured.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.insurance.assured.common.extensions.toResult
import com.insurance.assured.common.resource.onSuccess
import com.insurance.assured.domain.models.card.CardModel
import com.insurance.assured.domain.usecases.CleanCash
import com.insurance.assured.domain.usecases.card.DeleteUserCardUseCase
import com.insurance.assured.domain.usecases.card.GetUserCardUseCase
import com.insurance.assured.domain.usecases.card.InsertUserCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val cleanCash: CleanCash,
    private val deleteUserCardUseCase: DeleteUserCardUseCase,
    private val getUserCardUseCase: GetUserCardUseCase,
    private val insertUserCardUseCase: InsertUserCardUseCase
) : ViewModel() {
    private val currentUser get() = Firebase.auth.currentUser

    private val _state = MutableStateFlow<List<ProfileListItem>>(listOf())
    val state get() = _state.asStateFlow()

    private val _payload = MutableStateFlow(ProfilePageLoad())

    private val refreshData = MutableSharedFlow<Boolean>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    init {
        viewModelScope.launch {
            merge(
                refreshData,
            ).flatMapLatest { refresh ->
                getUserCardUseCase.invoke(currentUser!!.email!!).toResult()
            }.collectLatest {
                _payload.value = _payload.value.copy(card = it)
            }
        }
        viewModelScope.launch {
            _payload.collectLatest { data ->
                _state.value = buildList(data)
            }
        }
    }


    fun longOut() {
        Firebase.auth.signOut()
        cleanCash.invoke()
    }

    fun refresh() {
        if (checkUser()) {
            refreshData.tryEmit(true)
        } else {
            _payload.value = _payload.value.copy(profile = buildNonUserData())
        }
    }

    fun deleteCard(userId: String, cardToken: String) {
        viewModelScope.launch {
            deleteUserCardUseCase.invoke(userId, cardToken)
            refresh()
        }
    }

    private fun checkUser(): Boolean {
        return currentUser != null
    }

    private fun buildList(data: ProfilePageLoad): List<ProfileListItem> {
        if (currentUser == null) {
            return buildNonUserData()
        }

        val list = mutableListOf<ProfileListItem>()

        list.add(ProfileListItem.UserItem(currentUser?.email!!))

        list.add(ProfileListItem.TitleItem("Change personal data", "security your account"))

        list.add(ProfileListItem.ChangeEmailItem)
        list.add(ProfileListItem.ChangePassItem)

        list.add(ProfileListItem.TitleItem("Payment Method", "your card"))

        if (data.card != null) {
            data.card.onSuccess {
                it.forEach {
                    list.add(
                        ProfileListItem.CardItem(
                            it.userId,
                            it.cardLastNum,
                            it.cardType,
                            it.cardToken
                        )
                    )
                }
            }
        }

        list.add(ProfileListItem.AddCardItem)
        list.add(ProfileListItem.LogOutItem)

        list.add(ProfileListItem.SpaceItem)
        return list
    }

    private fun buildNonUserData(): List<ProfileListItem> {
        return listOf(ProfileListItem.NoUserItem)
    }
}