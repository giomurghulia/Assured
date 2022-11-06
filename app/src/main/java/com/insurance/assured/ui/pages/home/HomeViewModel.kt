package com.insurance.assured.ui.pages.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.insurance.assured.common.extensions.toResult
import com.insurance.assured.common.resource.data
import com.insurance.assured.common.utils.onInit
import com.insurance.assured.domain.usecases.bannerusecases.GetBannersUseCase
import com.insurance.assured.domain.usecases.checkoutsusecase.DeleteCheckoutUseCase
import com.insurance.assured.domain.usecases.checkoutsusecase.GetUnfinishedCheckoutsUseCase
import com.insurance.assured.domain.usecases.plansusecases.getdata.GetHotPlansUseCase
import com.insurance.assured.ui.mappers.toPresenterModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBannersUseCase: GetBannersUseCase,
    private val getHotPlansUseCase: GetHotPlansUseCase,
    private val getUnfinishedCheckoutsUseCase: GetUnfinishedCheckoutsUseCase,
    private val homePageListBuilder: HomePageListBuilder,
    private val deleteCheckoutUseCase: DeleteCheckoutUseCase
) : ViewModel() {
    private val _payload = MutableStateFlow(HomePagePayload())

    private val _state = MutableStateFlow<List<HomeListItem>>(listOf())
    val state get() = _state.asStateFlow()

    private val refreshData = MutableSharedFlow<Boolean>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val refreshMainBanners = MutableSharedFlow<Boolean>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )


    init {
        viewModelScope.launch {
            merge(
                onInit().map { false },
                refreshData,
                refreshMainBanners
            ).flatMapLatest { refresh ->
                getBannersUseCase.invoke(refresh).toResult()
            }.collectLatest {
                _payload.value = _payload.value.copy(mainBanners = it)
            }
        }

        viewModelScope.launch {
            merge(
                onInit().map { false },
                refreshData,
            ).flatMapLatest { refresh ->
                val pendingCheckouts = getUnfinishedCheckoutsUseCase.invoke()
                val result = if (pendingCheckouts.isNotEmpty()) {
                    pendingCheckouts.map { it.toPresenterModel() } to null
                } else {
                    null to getHotPlansUseCase.invoke().data?.map {
                        it.toPresenterModel()
                    }
                }

                flow { emit(result) }
            }.collectLatest { (pendingCheckouts, hotPans) ->
                _payload.value =
                    _payload.value.copy(unfinishedCheckout = pendingCheckouts, hotBanners = hotPans)
            }
        }

        viewModelScope.launch {
            _payload.collectLatest { data ->
                _state.value = homePageListBuilder.buildList(data)
            }
        }

    }

    fun refresh() {
        refreshData.tryEmit(true)
    }

    fun refreshMainBanners() {
        refreshMainBanners.tryEmit(true)
    }

    fun deleteUnfinishedCheckout(id: Int) {
        viewModelScope.launch {
            deleteCheckoutUseCase.invoke(id)
        }
        refreshData.tryEmit(false)
    }
}