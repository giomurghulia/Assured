package com.insurance.assured.ui.planslist

import com.insurance.assured.common.enums.InsuranceCategory
import com.insurance.assured.ui.enums.SelectState
import com.insurance.assured.ui.presentationmodels.planlist.FilterItemModel
import javax.inject.Inject


class InitialFilterList @Inject constructor() {
    operator fun invoke() = listOf(
        FilterItemModel(
            1,
            InsuranceCategory.HOT_OFFERS,
            SelectState.SELECTED
        ),
        FilterItemModel(
            2,
            InsuranceCategory.LIFE
        ),
        FilterItemModel(
            3,
            InsuranceCategory.PET
        ),
        FilterItemModel(
            4,
            InsuranceCategory.HOUSE
        ),
        FilterItemModel(
            5, InsuranceCategory.VEHICLE,
        )
    )
}