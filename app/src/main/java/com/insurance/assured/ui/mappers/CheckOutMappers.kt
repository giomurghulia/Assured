package com.insurance.assured.ui.mappers

import com.insurance.assured.common.enums.InsuranceCategory.Companion.insuranceCategory
import com.insurance.assured.domain.models.checkout.CheckoutDomainModel
import com.insurance.assured.ui.presentationmodels.planlist.PlanListItemModel
import com.insurance.assured.ui.presentationmodels.sharedmodel.CheckoutModel

fun CheckoutModel.toDomainModel(): CheckoutDomainModel {
    with(insurancePacket!!) {
        return CheckoutDomainModel(
            id,
            title,
            totalMoney,
            category.toString().lowercase(),
            slogan,
            monthlyPayment,
            image,
            feats,
            durationMonth,
            userId!!,
            idList!!
        )
    }
}

fun CheckoutDomainModel.toPresenterModel() = CheckoutModel(
    PlanListItemModel(
        id,
        title,
        maxMoney,
        typeSpecInfo,
        icon,
        monthlyPayment,
        feats,
        type.insuranceCategory(),
        durationMonth
    ), userId, idList
)