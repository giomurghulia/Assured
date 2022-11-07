package com.insurance.assured.ui.profile

sealed class ProfileListItem(val viewType: ViewType) {
    enum class ViewType {
        USER,
        NO_USER,
        CHANGE_EMAIL,
        CHANGE_PASS,
        ADD_CARD,
        CARD,
        TITLE,
        SPACE,
        LOG_OUT
    }

    object NoUserItem : ProfileListItem(ViewType.NO_USER)
    object ChangeEmailItem : ProfileListItem(ViewType.CHANGE_EMAIL)
    object ChangePassItem : ProfileListItem(ViewType.CHANGE_PASS)
    object AddCardItem : ProfileListItem(ViewType.ADD_CARD)
    object SpaceItem : ProfileListItem(ViewType.SPACE)
    object LogOutItem : ProfileListItem(ViewType.LOG_OUT)

    data class UserItem(
        val email: String
    ) : ProfileListItem(ViewType.USER)

    data class TitleItem(
        val title: String,
        val subTitle: String
    ) : ProfileListItem(ViewType.TITLE)

    data class CardItem(
        val userId: String,
        val cardLastNum: String,
        val cardType: String,
        val cardToken: String
    ) : ProfileListItem(ViewType.CARD)
}