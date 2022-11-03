package com.insurance.assured.domain.usecases.plansusecases.filterdata

import javax.inject.Inject

data class FilterDataUseCase @Inject constructor(
    val filterHousePlansUseCase: FilterHousePlansUseCase,
    val filterPetPlansUseCase: FilterPetPlansUseCase,
    val filterVehicleUseCase: FilterVehicleUseCase,
    val filterLifePlansUseCase: FilterLifePlansUseCase
)