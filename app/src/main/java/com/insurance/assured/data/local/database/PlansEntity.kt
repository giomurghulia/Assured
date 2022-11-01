package com.insurance.assured.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicle_insurance")
data class VehiclePlansEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val maxAmount: Float,
    val monthPrice: Float,
    val innerType: String,
    val periodMonth: Int,
    val rating: Int,
    val features: List<String>,
    val icon: String
)

@Entity(tableName = "pet_insurance")
data class PetPlansEntity (
    @PrimaryKey
    val id: Int,
    val title: String,
    val maxAmount: Float,
    val monthPrice: Float,
    val innerType: String,
    val periodMonth: Int,
    val rating: Int,
    val features: List<String>,
    val icon: String
)

@Entity(tableName = "house_insurance")
data class HousePlansEntity (
    @PrimaryKey
    val id: Int,
    val title: String,
    val maxAmount: Float,
    val monthPrice: Float,
    val innerType: String,
    val periodMonth: Int,
    val rating: Int,
    val features: List<String>,
    val icon: String
)

@Entity(tableName = "life_insurance")
data class LifePlansEntity (
    @PrimaryKey
    val id: Int,
    val title: String,
    val maxAmount: Float,
    val monthPrice: Float,
    val periodMonth: Int,
    val personCount: Int,
    val rating: Int,
    val features: List<String>,
    val icon: String
)
