package com.insurance.assured.ui.pages.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insurance.assured.common.extensions.toResult
import com.insurance.assured.common.resource.Resource
import com.insurance.assured.common.resource.Result.*
import com.insurance.assured.common.utils.onInit
import com.insurance.assured.domain.usecases.bannerusecases.GetBannersUseCase
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
    private val homePageListBuilder: HomePageListBuilder
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

    private val refreshCardBanners = MutableSharedFlow<Boolean>(
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
                refreshCardBanners
            ).flatMapLatest { refresh ->
                flow {
                    val responce = getHotPlansUseCase.invoke()

                    when (responce) {
                        is Resource.Success -> {
                            val data = responce.model.map {
                                it.toPresenterModel()
                            }
                            emit(Success(data))
                        }
                        is Resource.Error -> {

                        }
                    }
                }
            }.collectLatest {
                _payload.value = _payload.value.copy(hotBanners = it)
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

    fun refreshCardBanners() {
        refreshCardBanners.tryEmit(true)
    }

}