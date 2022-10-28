package com.insurance.assured.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insurance.assured.Result
import com.insurance.assured.domain.models.banner.BannersModel
import com.insurance.assured.domain.usecases.bannerusecases.GetBannersUseCase
import com.insurance.assured.toResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBannersUseCase: GetBannersUseCase
) : ViewModel() {

    private val _banners = MutableStateFlow<List<Banners>>(emptyList())
    val banners get() = _banners.asStateFlow()

    fun getBanners() {
        viewModelScope.launch {
            getBannersUseCase.invoke()
                .toResult()
                .collect {
                    when (it) {
                        is Result.Success -> {
                            _banners.emit(buildList(it.data))
                        }
                        is Result.Loading -> {
                        }
                        is Result.Error -> {
                        }
                    }

                }
        }
    }

    private fun buildList(products: List<BannersModel>): List<Banners> {
        val items = mutableListOf<Banners>()

        items.addAll(products.map { Banners(it.banner, it.title) })
        return items
    }


}