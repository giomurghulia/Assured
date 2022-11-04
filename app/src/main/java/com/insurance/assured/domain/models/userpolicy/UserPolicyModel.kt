package com.insurance.assured.domain.models.userpolicy

data class UserPolicyModel(
    val id: String,
    val policy_number: String,
    val policy: String,
    val type: String,
    val title: String,
    val start_date: Int,
    val finish_date: Int,
    val banner: String,
    val paid: Int,
    val all: Int,
    val paid_amount: Int,
    val all_amount: Int
)