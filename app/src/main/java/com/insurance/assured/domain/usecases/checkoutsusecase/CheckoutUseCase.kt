package com.insurance.assured.domain.usecases.checkoutsusecase

import javax.inject.Inject

data class CheckoutUseCase @Inject constructor(
    val deleteCheckoutUseCase: DeleteCheckoutUseCase,
    val getUnfinishedCheckoutsUseCase: GetUnfinishedCheckoutsUseCase,
    val getUserPurchasedItemsUseCase: GetUserPurchasedItemsUseCase,
    val insertUnfinishedCheckoutUseCase: InsertUnfinishedCheckoutUseCase,
    val insertUserPurchasedItemsUseCase: InsertUserPurchasedItemsUseCase
)
