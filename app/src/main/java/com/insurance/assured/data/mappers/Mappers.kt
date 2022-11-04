package com.insurance.assured.data.mappers


import com.insurance.assured.common.enums.HouseInsuranceType.Companion.toHouseInsuranceType
import com.insurance.assured.common.enums.InsuranceCategory
import com.insurance.assured.common.enums.InsuranceCategory.Companion.insuranceCategory
import com.insurance.assured.common.enums.VehicleInsuranceType.Companion.toVehicleInsuranceType
import com.insurance.assured.data.local.database.HousePlansEntity
import com.insurance.assured.data.local.database.LifePlansEntity
import com.insurance.assured.data.local.database.PetPlansEntity
import com.insurance.assured.data.local.database.VehiclePlansEntity
import com.insurance.assured.data.remote.dtos.plans.PlansDto
import com.insurance.assured.domain.models.plans.*

fun PlansDto.toDomainLife(): PlansModel<LifeSpec> = PlansModel(
    id,
    title,
    maxAmount,
    monthPrice,
    features,
    InsuranceCategory.HEALTH,
    periodMonth,
    icon,
    LifeSpec(personCount ?: 0)
)

fun PlansDto.toDomainHouse(): PlansModel<HouseSpecs> = PlansModel(
    id,
    title,
    maxAmount,
    monthPrice,
    features,
    InsuranceCategory.HOUSE,
    periodMonth,
    icon,
    HouseSpecs(innerType!!.toHouseInsuranceType())
)

fun PlansDto.toDomainPet(): PlansModel<PetSpec> = PlansModel(
    id,
    title,
    maxAmount,
    monthPrice,
    features,
    InsuranceCategory.PET,
    periodMonth,
    icon,
    PetSpec(innerType!!)
)

fun PlansDto.toDomainVehicle(): PlansModel<VehicleSpecs> = PlansModel(
    id,
    title,
    maxAmount,
    monthPrice,
    features,
    InsuranceCategory.VEHICLE,
    periodMonth,
    icon,
    VehicleSpecs(innerType!!.toVehicleInsuranceType())
)

fun List<PlansDto>.toDomainHotList(): List<PlansModel<*>> = this.map {
    when (it.type!!.insuranceCategory()) {
        InsuranceCategory.VEHICLE -> it.toDomainVehicle()
        InsuranceCategory.PET -> it.toDomainPet()
        InsuranceCategory.HOUSE -> it.toDomainHouse()
        else -> it.toDomainLife()
    }
}


fun PlansDto.toVehicleEntity(): VehiclePlansEntity = VehiclePlansEntity(
    id,
    title,
    maxAmount,
    monthPrice,
    innerType!!,
    periodMonth,
    rating,
    features,
    icon
)

fun PlansDto.toHouseEntity(): HousePlansEntity = HousePlansEntity(
    id,
    title,
    maxAmount,
    monthPrice,
    innerType!!,
    periodMonth,
    rating,
    features,
    icon
)

fun PlansDto.toPetEntity(): PetPlansEntity = PetPlansEntity(
    id,
    title,
    maxAmount,
    monthPrice,
    innerType!!,
    periodMonth,
    rating,
    features,
    icon
)

fun PlansDto.toLifeEntity(): LifePlansEntity = LifePlansEntity(
    id,
    title,
    maxAmount,
    monthPrice,
    personCount ?: 0,
    periodMonth,
    rating,
    features,
    icon
)

fun LifePlansEntity.toDomain() = PlansModel(
    id,
    title,
    maxAmount,
    monthPrice,
    features,
    InsuranceCategory.HEALTH,
    periodMonth,
    icon,
    LifeSpec(personCount)
)

fun HousePlansEntity.toDomain(): PlansModel<HouseSpecs> = PlansModel(
    id,
    title,
    maxAmount,
    monthPrice,
    features,
    InsuranceCategory.HOUSE,
    periodMonth,
    icon,
    HouseSpecs(innerType.toHouseInsuranceType())
)

fun PetPlansEntity.toDomain(): PlansModel<PetSpec> = PlansModel(
    id,
    title,
    maxAmount,
    monthPrice,
    features,
    InsuranceCategory.PET,
    periodMonth,
    icon,
    PetSpec(innerType)
)

fun VehiclePlansEntity.toDomain(): PlansModel<VehicleSpecs> = PlansModel(
    id,
    title,
    maxAmount,
    monthPrice,
    features,
    InsuranceCategory.VEHICLE,
    periodMonth,
    icon,
    VehicleSpecs(innerType.toVehicleInsuranceType())
)