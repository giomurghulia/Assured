package com.insurance.assured.ui.policy


sealed class PolicyListItem(val viewType: ViewType) {
    enum class ViewType {
        NO_POLICY,
        PET_BANNER,
        CASHLESS_BANNER,
        FULL_AMOUNT,
        POLICY_NUMBER,
        POLICY,
        TITLE
    }

    object NoPolicyItem : PolicyListItem(ViewType.NO_POLICY)

    data class PetBannersItem(
        val title: String
    ) : PolicyListItem(ViewType.PET_BANNER)

    object CashlessBannerItem : PolicyListItem(ViewType.CASHLESS_BANNER)

    data class FullAmountItem(
        val fullAmount: String
    ) : PolicyListItem(ViewType.FULL_AMOUNT)

    data class PolicyNumberItem(
        val all: String,
        val house: String,
        val health: String,
        val car: String,
        val pet: String
    ) : PolicyListItem(ViewType.POLICY_NUMBER)

    data class PolicyItem(
        val id: String,
        val title: String,
        val type: String,
        val banner: String
    ) : PolicyListItem(ViewType.POLICY)

    data class TitleItem(
        val title: String,
        val subTitle: String
    ) : PolicyListItem(ViewType.TITLE)
}