package com.insurance.assured.domain.usecases.checkoutsusecase

import com.insurance.assured.domain.models.checkout.CheckoutDomainModel
import com.insurance.assured.domain.repositorys.CheckoutRepository
import javax.inject.Inject

class InsertUserPurchasedItemsUseCase @Inject constructor(private val repository: CheckoutRepository) {
    suspend operator fun invoke(checkoutModel: CheckoutDomainModel, token: String) =
        repository.insertFinishedCheckout(checkoutModel, token)

}