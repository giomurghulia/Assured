package com.insurance.assured.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insurance.assured.*
import com.insurance.assured.common.extensions.toResult
import com.insurance.assured.domain.models.banner.BannersModel
import com.insurance.assured.domain.usecases.bannerusecases.GetBannersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

fun onInit() = flow { emit(Unit) }

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBannersUseCase: GetBannersUseCase,
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
                getBannersUseCase.invoke(refresh).toResult()
            }.collectLatest {
                _payload.value = _payload.value.copy(carBanners = it)
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