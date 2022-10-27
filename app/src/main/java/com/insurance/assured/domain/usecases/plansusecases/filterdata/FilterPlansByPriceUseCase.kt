package com.insurance.assured.domain.usecases.plansusecases.filterdata

import com.insurance.assured.domain.models.plans.PlansModel
import com.insurance.assured.domain.models.plans.Spec

class FilterPlansByPriceUseCase {
    suspend operator fun invoke(
        list: List<PlansModel<Spec>>,
        startingPrice: Float,
        endingPrice: Float
    ) = list.filter { it.monthlyPayment in startingPrice..endingPrice }
}