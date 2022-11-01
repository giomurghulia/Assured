package com.insurance.assured.domain.usecases.plansusecases.filterdata

import com.insurance.assured.common.enums.HouseInsuranceType
import com.insurance.assured.domain.models.plans.HouseSpecs
import com.insurance.assured.domain.models.plans.PlansModel
import com.insurance.assured.domain.models.plans.Spec

class FilterHousesByTypeUseCase {
    suspend operator fun invoke(list: List<PlansModel<HouseSpecs>>, type: HouseInsuranceType) =
        list.filter { it.typeSpecs.type == type }
}