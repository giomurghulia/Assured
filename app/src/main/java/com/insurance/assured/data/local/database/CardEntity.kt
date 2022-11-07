package com.insurance.assured.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "card")
data class CardEntity(
    val userId: String,
    val cardLastNum: String,
    val cardType: String,
    @PrimaryKey
    val cardToken: String
)