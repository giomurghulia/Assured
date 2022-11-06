package com.insurance.assured.data.local.database

import androidx.room.Entity

@Entity(tableName = "purchased_items", primaryKeys = ["id", "userToken"])
data class PurchasedItemsEntity(
    val id: Int,
    val userToken: String,
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