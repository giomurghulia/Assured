package com.insurance.assured.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "unfinished_checkout")
data class UnfinishedCheckoutEntity(
    @PrimaryKey(autoGenerate = true)
    val databaseId: Int = 0,
    val id: Int?,
    val type: String?,
    val userId: String?,
    val itemIds: List<String>?,
    val processStep: Int?
)