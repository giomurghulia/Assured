package com.insurance.assured.domain.usecases.plansusecases.filterdata

import com.insurance.assured.common.enums.HouseInsuranceType
import com.insurance.assured.common.enums.VehicleInsuranceType
import com.insurance.assured.domain.models.plans.HouseSpecs
import com.insurance.assured.domain.models.plans.PlansModel
import com.insurance.assured.domain.models.plans.VehicleSpecs
import javax.inject.Inject

class FilterVehicleUseCase @Inject constructor() {
    suspend operator fun invoke(
        list: List<PlansModel<VehicleSpecs>>,
        type: VehicleInsuranceType?,
        moneyFrom: Float,
        moneyTo: Float,
        priceFrom: Float,
        priceTo: Float,
        durationFrom: Int,
        durationTo: Int
    ) =
        list.filter {
            (it.typeSpecs.type == type || type == null)
                    && it.monthlyPayment >= priceFrom
                    && it.monthlyPayment <= priceTo
                    && it.maxMoney >= moneyFrom &&
                    it.maxMoney <= moneyTo
                    && it.durationMonth in durationFrom..durationTo
        }
}