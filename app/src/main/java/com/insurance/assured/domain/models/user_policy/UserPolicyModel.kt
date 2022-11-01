package com.insurance.assured.domain.models.user_policy

data class UserPolicyModel(
    val health_insurance: List<PolicyModel>?,
    val house_insurance: List<PolicyModel>?,
    val vehicle_insurance: List<PolicyModel>?,
    val pet_insurance: List<PolicyModel>?
)