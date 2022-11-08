package com.insurance.assured.ui.pages.home

import com.insurance.assured.common.enums.InsuranceCategory
import com.insurance.assured.ui.presentationmodels.planlist.PlanListItemModel

sealed class HomeListItem(val viewType: ViewType) {
    enum class ViewType {
        MAIN_BANNERS,
        CATEGORIES,
        HOT_BANNER,
        CASHLESS_BANNER,
        TITLE,
        SHIMMER_BANNER,
        ERROR_MAIN_BANNER,
        ERROR_CAR_BANNER,
        UNFINISHED_CHECKOUT,
        SPACE
    }

    data class MainBannersItem(
        val banners: List<Banner>
    ) : HomeListItem(ViewType.MAIN_BANNERS)

    object CategoriesItem : HomeListItem(ViewType.CATEGORIES)

    data class HotBannerItem(
        val id: Int = -1,
        val title: String = "",
        val totalMoney: Float = 0.0f,
        val slogan: String = "",
        val image: String = "",
        val monthlyPayment: Float = 0f,
        val feats: List<String> = emptyList(),
        val category: InsuranceCategory = InsuranceCategory.DEFAULT,
        val durationMonth: Int = 0
    ) : HomeListItem(ViewType.HOT_BANNER)

    object CashlessItem : HomeListItem(ViewType.CASHLESS_BANNER)

    data class TitleItem(
        val title: String,
        val subTitle: String
    ) : HomeListItem(ViewType.TITLE)

    object ShimmerBannerItem : HomeListItem(ViewType.SHIMMER_BANNER)

    object ErrorCarBannerItem : HomeListItem(ViewType.ERROR_CAR_BANNER)

    object ErrorMainBannerItem : HomeListItem(ViewType.ERROR_MAIN_BANNER)

    data class UnfinishedCheckoutItem(
        val insurancePacket: PlanListItemModel? = null,
        val userId: String? = null,
        val idList: List<String>? = null
    ) : HomeListItem(ViewType.UNFINISHED_CHECKOUT)

    object SpaceItem : HomeListItem(ViewType.SPACE)

}