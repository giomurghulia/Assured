package com.insurance.assured.ui.mappers

import com.insurance.assured.common.enums.HouseInsuranceType.Companion.toHouseInsuranceType
import com.insurance.assured.common.enums.VehicleInsuranceType.Companion.toVehicleInsuranceType
import com.insurance.assured.domain.models.plans.*
import com.insurance.assured.ui.presentationmodels.planlist.PlanListItemModel

fun PlansModel<PetSpec>.petToPresenterModel() =
    PlanListItemModel(
        id = id,
        title = title,
        totalMoney = maxMoney,
        slogan = typeSpecs.pet,
        image = icon,
        monthlyPayment = monthlyPayment,
        feats = feats,
        category = category,
        durationMonth = durationMonth
    )

fun PlansModel<HouseSpecs>.houseToPresenterModel() =
    PlanListItemModel(
        id = id,
        title = title,
        totalMoney = maxMoney,
        slogan = typeSpecs.type.toString(),
        image = icon,
        monthlyPayment = monthlyPayment,
        feats = feats,
        category = category,
        durationMonth = durationMonth
    )

fun PlansModel<VehicleSpecs>.vehicleToPresenterModel() =
    PlanListItemModel(
        id = id,
        title = title,
        totalMoney = maxMoney,
        slogan = typeSpecs.type.toString(),
        image = icon,
        monthlyPayment = monthlyPayment,
        feats = feats,
        category = category,
        durationMonth = durationMonth
    )

fun PlansModel<LifeSpec>.lifeToPresenterModel() =
    PlanListItemModel(
        id = id,
        title = title,
        totalMoney = maxMoney,
        slogan = typeSpecs.personCount.toString(),
        image = icon,
        monthlyPayment = monthlyPayment,
        feats = feats,
        category = category,
        durationMonth = durationMonth
    )

fun PlansModel<*>.toPresenterModel() =
    when (typeSpecs) {
        is PetSpec -> (this as PlansModel<PetSpec>).petToPresenterModel()
        is HouseSpecs -> (this as PlansModel<HouseSpecs>).houseToPresenterModel()
        is LifeSpec -> (this as PlansModel<LifeSpec>).lifeToPresenterModel()
        is VehicleSpecs -> (this as PlansModel<VehicleSpecs>).vehicleToPresenterModel()
        else -> PlanListItemModel()
    }


fun PlanListItemModel.toHouseDomainModel() = PlansModel(
    id,
    title,
    totalMoney,
    monthlyPayment,
    feats,
    category,
    durationMonth,
    image,
    HouseSpecs(slogan.toHouseInsuranceType())
)

fun PlanListItemModel.toVehicleDomainModel() = PlansModel(
    id,
    title,
    totalMoney,
    monthlyPayment,
    feats,
    category,
    durationMonth,
    image,
    VehicleSpecs(slogan.toVehicleInsuranceType())
)

fun PlanListItemModel.toLifeDomainModel() = PlansModel(
    id,
    title,
    totalMoney,
    monthlyPayment,
    feats,
    category,
    durationMonth,
    image,
    LifeSpec(slogan.toInt())
)

fun PlanListItemModel.toPetDomainModel() = PlansModel(
    id,
    title,
    totalMoney,
    monthlyPayment,
    feats,
    category,
    durationMonth,
    image,
    PetSpec(slogan)
)