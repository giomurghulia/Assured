package com.insurance.assured.domain.usecases.plansusecases.getdata

import javax.inject.Inject

data class GetDataUseCase @Inject constructor(
    val getHotPlans: GetHotPlansUseCase,
    val getHousePlans: GetHousePlansUseCase,
    val getPetPlans: GetPetPlansUseCase,
    val getLifePlans: GetLifePlansUseCase,
    val getVehiclePlans: GetVehiclePlansUseCase
)
