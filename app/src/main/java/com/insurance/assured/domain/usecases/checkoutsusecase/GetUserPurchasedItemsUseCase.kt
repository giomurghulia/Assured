package com.insurance.assured.domain.usecases.checkoutsusecase

import com.insurance.assured.domain.repositorys.CheckoutRepository
import javax.inject.Inject

class GetUserPurchasedItemsUseCase @Inject constructor(private val repository: CheckoutRepository) {
    suspend operator fun invoke(token: String) = repository.getMyInsurances(token)
}