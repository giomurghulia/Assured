package com.insurance.assured.domain.models.plans

import com.insurance.assured.common.enums.InsuranceCategory

data class PlansModel<T : Spec>(
    val id: Int,
    val title: String,
    val maxMoney: Float,
    val monthlyPayment: Float,
    val feats: List<String>,
    val category: InsuranceCategory,
    val durationMonth: Int,
    val icon: String,
    val typeSpecs: T
)