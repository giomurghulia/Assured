package com.insurance.assured.domain.models.checkout

data class CheckoutDomainModel(
    val id: Int,
    val title: String,
    val maxMoney: Float,
    val type: String,
    val typeSpecInfo: String,
    val monthlyPayment: Float,
    val icon: String,
    val feats: List<String>,
    val durationMonth: Int,
    val userId: String,
    val idList: List<String>,
    val boughtTime: Long = System.currentTimeMillis()
)