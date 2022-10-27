package com.insurance.assured.ui.pascode

sealed class PassNumberListItem(val viewType: ViewType) {
    enum class ViewType {
        NUMBER,
        FINGER,
        DELETE,
    }

    data class NumberItem(
        val id: Int
    ) : PassNumberListItem(ViewType.NUMBER)

    object FingerItem : PassNumberListItem(ViewType.FINGER)
    object DeleteItem : PassNumberListItem(ViewType.DELETE)

}
