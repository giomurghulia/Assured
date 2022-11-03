package com.insurance.assured.domain.usecases.plansusecases.filterdata

import com.insurance.assured.domain.models.plans.LifeSpec
import com.insurance.assured.domain.models.plans.PlansModel
import javax.inject.Inject

class FilterLifePlansUseCase @Inject constructor() {
    suspend operator fun invoke(
        list: List<PlansModel<LifeSpec>>,
        personCountFrom: Int,
        personCountTo: Int,
        moneyFrom: Float,
        moneyTo: Float,
        priceFrom: Float,
        priceTo: Float,
        durationFrom: Int,
        durationTo: Int
    ) =
        list.filter {
            it.typeSpecs.personCount in personCountFrom..personCountTo
                    && it.monthlyPayment >= priceFrom
                    && it.monthlyPayment <= priceTo
                    && it.maxMoney >= moneyFrom &&
                    it.maxMoney <= moneyTo
                    && it.durationMonth in durationFrom..durationTo
        }
}