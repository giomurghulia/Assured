package com.insurance.assured.ui.home

import com.insurance.assured.common.enums.InsuranceCategory

sealed class HomeListItem(val viewType: ViewType) {
    enum class ViewType {
        MAIN_BANNERS,
        CATEGORIES,
        HOT_BANNER,
        CASHLESS_BANNER,
        SHIMMER_BANNER,
        ERROR_MAIN_BANNER,
        ERROR_CAR_BANNER
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

    object ShimmerBannerItem : HomeListItem(ViewType.SHIMMER_BANNER)

    object ErrorCarBannerItem : HomeListItem(ViewType.ERROR_CAR_BANNER)

    object ErrorMainBannerItem : HomeListItem(ViewType.ERROR_MAIN_BANNER)

}