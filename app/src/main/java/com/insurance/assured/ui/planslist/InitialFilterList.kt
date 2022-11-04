package com.insurance.assured.ui.planslist

import com.insurance.assured.common.enums.InsuranceCategory
import com.insurance.assured.ui.enums.SelectState
import com.insurance.assured.ui.presentationmodels.planlist.FilterItemModel
import com.insurance.assured.ui.presentationmodels.planlist.PlanListItemModel
import javax.inject.Inject


class InitialListsProvider @Inject constructor() {
    fun getFilterList() = listOf(
        FilterItemModel(
            1,
            InsuranceCategory.HOT_OFFERS,
            SelectState.SELECTED
        ),
        FilterItemModel(
            2,
            InsuranceCategory.HEALTH
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

    fun getShimmerList() = listOf(
        PlanListItemModel(),
        PlanListItemModel(),
        PlanListItemModel(),
        PlanListItemModel(),
        PlanListItemModel()
    )

    fun getHouseItems() = listOf("All", "House", "Country House", "Apartment")

    fun getVehicleItems() = listOf("All", "Car", "Moto", "Truck")

    fun getPetItems() = listOf("All", "Cat", "Mouse", "Dog")

    fun getLifeItems() = listOf("All", "1", "2", "3", "4", "5+")

}