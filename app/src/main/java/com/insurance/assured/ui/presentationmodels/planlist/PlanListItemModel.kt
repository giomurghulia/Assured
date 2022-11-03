package com.insurance.assured.ui.presentationmodels.planlist

import com.insurance.assured.common.enums.InsuranceCategory

data class PlanListItemModel(
    val id: Int = -1,
    val title: String = "",
    val totalMoney: Float = 0.0f,
    val slogan: String = "",
    val image: String = "",
    val monthlyPayment: Float = 0f,
    val feats: List<String> = emptyList(),
    val category: InsuranceCategory = InsuranceCategory.DEFAULT,
    val durationMonth: Int = 0
)