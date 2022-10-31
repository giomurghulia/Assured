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

    private val _state = MutableStateFlow(buildList())
    val stat get() = _state.asStateFlow()

    private val mainBanners = mutableListOf<Banner>()

    private var carBanners: HomeListItem.CarBannerItem? = null

    private var healthBanners: HomeListItem.HeathBannerItem? = null

    fun getDate() {
        getMainBanners()
        getCarBanners()
        getHealthBanners()
    }

    private fun getMainBanners() {
        viewModelScope.launch {
            getBannersUseCase.invoke().toResult().collect {
                when (it) {
                    is Result.Success -> {
                        setMainBanners(it.data)
                        _state.emit(buildList())
                    }
                    is Result.Loading -> {
                    }
                    is Result.Error -> {
                    }
                }
            }
        }
    }

    private fun getCarBanners() {
        viewModelScope.launch {
            getBannersUseCase.invoke().toResult().collect {
                when (it) {
                    is Result.Success -> {
                        setCarBanners(it.data)
                        _state.emit(buildList())
                    }
                    is Result.Loading -> {
                    }
                    is Result.Error -> {
                    }
                }
            }
        }
    }

    private fun getHealthBanners() {
        viewModelScope.launch {
            getBannersUseCase.invoke().toResult().collect {
                when (it) {
                    is Result.Success -> {
                        setHeathBanners(it.data)
                        _state.emit(buildList())
                    }
                    is Result.Loading -> {
                    }
                    is Result.Error -> {
                    }
                }
            }
        }
    }

    private fun setMainBanners(banners: List<BannersModel>) {
        mainBanners.addAll(banners.map { Banner(it.id, it.banner, it.title) })

        buildList()
    }

    private fun setCarBanners(banners: List<BannersModel>) {
        val bannerModel = banners[0]
        carBanners =
            HomeListItem.CarBannerItem(bannerModel.id, bannerModel.banner, bannerModel.title)

        buildList()

    }

    private fun setHeathBanners(banners: List<BannersModel>) {
        val bannerModel = banners[0]
        healthBanners =
            HomeListItem.HeathBannerItem(bannerModel.id, bannerModel.banner, bannerModel.title)

        buildList()
    }

    private fun buildList(): List<HomeListItem> {
        val items = mutableListOf<HomeListItem>()

        if (!mainBanners.isNullOrEmpty()) {
            items.add(HomeListItem.MainBannersItem(mainBanners))
        } else {
            items.add(HomeListItem.ShimmerBannerItem)
        }

        items.add(HomeListItem.CategoriesItem)

        if (carBanners != null) {
            items.add(carBanners!!)
        } else {
            items.add(HomeListItem.ShimmerBannerItem)
        }

        if (healthBanners != null) {
            items.add(healthBanners!!)
        } else {
            items.add(HomeListItem.ShimmerBannerItem)
        }

        return items
    }


}