package com.insurance.assured.ui.home

import com.insurance.assured.domain.models.banner.BannersModel
import com.insurance.assured.common.resource.Result
import com.insurance.assured.common.resource.onError
import com.insurance.assured.common.resource.onLoading
import com.insurance.assured.common.resource.onSuccess

import javax.inject.Inject

class HomePageListBuilder @Inject constructor() {

    fun buildList(payload: HomePagePayload): List<HomeListItem> {
        return buildMainBannersList(payload.mainBanners)
            .plus(listOf(HomeListItem.CategoriesItem))
            .plus(buildCardBannersList(payload.carBanners))
    }

    private fun buildMainBannersList(banners: Result<List<BannersModel>>): List<HomeListItem> {
        val list = mutableListOf<HomeListItem>()

        banners.onSuccess { data ->
            list.addAll(
                listOf(
                    HomeListItem.MainBannersItem(
                        data.map { Banner(it.id, it.banner, it.title) }
                    )
                )
            )

        }

        banners.onError {
            list.addAll(listOf(HomeListItem.ErrorMainBannerItem))
        }

        banners.onLoading {
            list.addAll(listOf(HomeListItem.ShimmerBannerItem))
        }

        return list
    }

    private fun buildCardBannersList(banners: Result<List<BannersModel>>): List<HomeListItem> {
        val list = mutableListOf<HomeListItem>()

        banners.onSuccess { data ->
            list.addAll(listOfNotNull(data.firstOrNull()?.let { banner ->
                HomeListItem.HeathBannerItem(banner.id, banner.banner, banner.title)
            }))
        }

        banners.onError {
            list.addAll(listOf(HomeListItem.ErrorCarBannerItem))
        }

        banners.onLoading {
            list.addAll(listOf(HomeListItem.ShimmerBannerItem))
        }

        return list
    }

}