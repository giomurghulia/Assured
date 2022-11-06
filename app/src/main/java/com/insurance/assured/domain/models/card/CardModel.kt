package com.insurance.assured.domain.models.card

data class CardModel(
    val userId: String,
    val cardLastNum: String,
    val cardType: String,
    val cardToken: String
)