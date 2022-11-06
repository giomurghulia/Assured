package com.insurance.assured.ui.presentationmodels.sharedmodel

import com.insurance.assured.ui.presentationmodels.planlist.PlanListItemModel

data class CheckoutModel(
    val insurancePacket: PlanListItemModel? = null,
    val userId: String? = null,
    val idList: List<String>? = null
)
