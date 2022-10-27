package com.insurance.assured.domain.usecases.plansusecases.getdata

data class GetDataUseCase(
    val hotPlansUseCase: GetHotPlansUseCase,
    val getHousePlans: GetHousePlansUseCase,
    val getPetPlans: GetPetPlansUseCase,
    val getLifePlans: GetLifePlansUseCase,
    val getVehiclePlans: GetVehiclePlansUseCase
)