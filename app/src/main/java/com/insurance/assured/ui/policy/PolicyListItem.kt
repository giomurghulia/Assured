package com.insurance.assured.ui.policy


sealed class PolicyListItem(val viewType: ViewType) {
    enum class ViewType {
        NO_POLICY,
        PET_BANNER,
        CASHLESS_BANNER,
        USER_DATA,
        POLICY,
        TITLE,
        ERROR_USER_DATA,
        ERROR_POLICY
    }

    object NoPolicyItem : PolicyListItem(ViewType.NO_POLICY)

    data class PetBannersItem(
        val title: String
    ) : PolicyListItem(ViewType.PET_BANNER)

    object CashlessBannerItem : PolicyListItem(ViewType.CASHLESS_BANNER)

    data class UserDataItem(
        val id: String,
        val fullAmount: String,
        val all: String,
        val house: String,
        val health: String,
        val car: String,
        val pet: String
    ) : PolicyListItem(ViewType.USER_DATA)

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

    object ErrorUserDataItem : PolicyListItem(ViewType.ERROR_USER_DATA)

    object ErrorPolicyItem : PolicyListItem(ViewType.ERROR_POLICY)

}