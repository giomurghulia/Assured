package com.insurance.assured.ui.chat


sealed class ChatListItem(val viewType: ViewType) {
    enum class ViewType {
        USER_MESSAGE,
        ADMIN_MESSAGE
    }

    data class UserMessageItem(
        val message: String,
        val time: String
    ) : ChatListItem(ViewType.USER_MESSAGE)

    data class AdminMessageItem(
        val message: String,
        val time: String
    ) : ChatListItem(ViewType.ADMIN_MESSAGE)

}