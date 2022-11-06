package com.insurance.assured.ui.pages.home

import com.insurance.assured.domain.models.banner.BannersModel
import com.insurance.assured.common.resource.Result
import com.insurance.assured.common.resource.onError
import com.insurance.assured.common.resource.onLoading
import com.insurance.assured.common.resource.onSuccess
import com.insurance.assured.ui.presentationmodels.planlist.PlanListItemModel
import com.insurance.assured.ui.presentationmodels.sharedmodel.CheckoutModel

import javax.inject.Inject

class HomePageListBuilder @Inject constructor() {

    fun buildList(payload: HomePagePayload): List<HomeListItem> {
        return buildMainBannersList(payload.mainBanners)
            .plus(listOf(HomeListItem.CategoriesItem))
            .plus(HomeListItem.CashlessItem)
            .plus(buildRemainderList(payload.unfinishedCheckout, payload.hotBanners))
            .plus(HomeListItem.SpaceItem)

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

    private fun buildHotBannersList(banners: List<PlanListItemModel>?): List<HomeListItem> {
        val list = mutableListOf<HomeListItem>()

        if (banners != null) {
            list.add(HomeListItem.TitleItem("Special Offer", "find your"))
        }
        list.addAll(listOfNotNull(banners?.firstOrNull()?.let { banner ->
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

        return list
    }

    private fun buildRemainderList(
        unfinishedCheckout: List<CheckoutModel>?,
        hotBanners: List<PlanListItemModel>?
    ): List<HomeListItem> {
        val list = mutableListOf<HomeListItem>()

        if (unfinishedCheckout != null) {
            list.add(HomeListItem.TitleItem("Continue Checkout", "don't forgot"))
            list.addAll(unfinishedCheckout.map { item ->
                HomeListItem.UnfinishedCheckoutItem(
                    item.insurancePacket,
                    item.userId,
                    item.idList
                )
            })

        } else {
            list.addAll(buildHotBannersList(hotBanners))
        }

        return list
    }

}