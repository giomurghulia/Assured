package com.insurance.assured.ui.pages.home

import com.insurance.assured.domain.models.banner.BannersModel
import com.insurance.assured.common.resource.Result
import com.insurance.assured.common.resource.onError
import com.insurance.assured.common.resource.onLoading
import com.insurance.assured.common.resource.onSuccess
import com.insurance.assured.ui.presentationmodels.planlist.PlanListItemModel

import javax.inject.Inject

class HomePageListBuilder @Inject constructor() {

    fun buildList(payload: HomePagePayload): List<HomeListItem> {
        return buildMainBannersList(payload.mainBanners)
            .plus(listOf(HomeListItem.CategoriesItem))
            .plus(HomeListItem.CashlessItem)
            .plus(buildCardBannersList(payload.hotBanners))
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

    private fun buildCardBannersList(banners: Result<List<PlanListItemModel>>): List<HomeListItem> {
        val list = mutableListOf<HomeListItem>()

        banners.onSuccess { data ->
            list.addAll(listOfNotNull(data.firstOrNull()?.let { banner ->
                HomeListItem.HotBannerItem(
                    banner.id,
                    banner.title,
                    banner.totalMoney,
                    banner.slogan,
                    banner.image,
                    banner.monthlyPayment,
                    banner.feats,
                    banner.category,
                    banner.durationMonth
                )
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