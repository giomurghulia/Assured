package com.insurance.assured.data.mappers

import com.insurance.assured.data.local.database.PurchasedItemsEntity
import com.insurance.assured.data.local.database.UnfinishedCheckoutEntity
import com.insurance.assured.domain.models.checkout.CheckoutDomainModel

fun CheckoutDomainModel.toUnfinishedCheckout() = UnfinishedCheckoutEntity(
    id,
    title,
    maxMoney,
    type,
    typeSpecInfo,
    monthlyPayment,
    icon,
    feats,
    durationMonth,
    userId,
    idList
)

fun CheckoutDomainModel.toPurchasedCheckout(userToken: String) = PurchasedItemsEntity(
    id,
    userToken,
    title,
    maxMoney,
    type,
    typeSpecInfo,
    monthlyPayment,
    icon,
    feats,
    durationMonth,
    userId,
    idList,
    boughtTime
)

fun PurchasedItemsEntity.toDomainModel() = CheckoutDomainModel(
    id,
    title,
    maxMoney,
    type,
    typeSpecInfo,
    monthlyPayment,
    icon,
    feats,
    durationMonth,
    userId,
    idList,
    boughtTime
)

fun UnfinishedCheckoutEntity.toDomainModel() = CheckoutDomainModel(
    id,
    title,
    maxMoney,
    type,
    typeSpecInfo,
    monthlyPayment,
    icon,
    feats,
    durationMonth,
    userId,
    idList
)