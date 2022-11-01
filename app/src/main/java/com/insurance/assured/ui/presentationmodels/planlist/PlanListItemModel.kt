package com.insurance.assured.ui.presentationmodels.planlist

import com.insurance.assured.common.enums.InsuranceCategory

data class PlanListItemModel(
    val id: Int,
    val title: String,
    val totalMoney: Float,
    val slogan: String,
    val image: String,
    val monthlyPayment: Float,
    val feats: List<String>,
    val category: InsuranceCategory
)