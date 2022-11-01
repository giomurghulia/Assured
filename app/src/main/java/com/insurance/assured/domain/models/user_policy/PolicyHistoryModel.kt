package com.insurance.assured.domain.models.user_policy

data class PolicyHistoryModel(
    val type: String,
    val paid: Int,
    val all: Int
)