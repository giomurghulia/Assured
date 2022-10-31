package com.insurance.assured.ui.home

sealed class HomeListItem(val viewType: ViewType) {
    enum class ViewType {
        MAIN_BANNERS,
        CATEGORIES,
        CAR_BANNER,
        HEALTH_BANNER,
        SHIMMER_BANNER
    }

    data class MainBannersItem(
        val banners: List<Banner>
    ) : HomeListItem(ViewType.MAIN_BANNERS)

    object CategoriesItem : HomeListItem(ViewType.CATEGORIES)

    data class CarBannerItem(
        val id: String,
        val banners: String,
        val title: String
    ) : HomeListItem(ViewType.CAR_BANNER)

    data class HeathBannerItem(
        val id: String,
        val banners: String,
        val title: String
    ) : HomeListItem(ViewType.HEALTH_BANNER)

    object ShimmerBannerItem : HomeListItem(ViewType.SHIMMER_BANNER)

}