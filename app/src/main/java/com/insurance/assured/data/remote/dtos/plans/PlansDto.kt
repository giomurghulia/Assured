package com.insurance.assured.data.remote.dtos.plans

import com.squareup.moshi.Json


data class PlansDto(
    val id: Int,
    val title: String,
    @Json(name = "max_amount")
    val maxAmount: Float = 0f,
    @Json(name = "month_price")
    val monthPrice: Float,
    @Json(name = "inner_type")
    val innerType: String? = null,
    @Json(name = "period_month")
    val periodMonth: Int,
    @Json(name = "person_count")
    val personCount: Int? = null,
    val rating: Int,
    val type: String? = null,
    val features: List<String>,
    val icon: String
)