package com.insurance.assured.domain.models.user_policy

data class PolicyModel(
    val id: String,
    val policy_number: String,
    val title: String,
    val type: String,
    val start_date: Long,
    val finish_date: Long,
    val banner: String,
    val history: List<PolicyHistoryModel>
)