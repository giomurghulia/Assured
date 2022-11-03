package com.insurance.assured.domain.models.user_policy

data class UserDataModel(
    val id: String?,
    val full_amount: Int,
    val all_policy_number: Int,
    val health_insurance: Int,
    val house_insurance: Int,
    val vehicle_insurance: Int,
    val pet_insurance: Int
)