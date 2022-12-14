package com.insurance.assured.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "unfinished_checkouts")
data class UnfinishedCheckoutEntity(
    @PrimaryKey
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
    val idList: List<String>
)