package com.insurance.assured.domain.usecases.checkoutsusecase

import com.insurance.assured.domain.models.checkout.CheckoutDomainModel
import com.insurance.assured.domain.repositorys.CheckoutRepository
import javax.inject.Inject

class InsertUnfinishedCheckoutUseCase @Inject constructor(private val repo: CheckoutRepository) {
    suspend operator fun invoke(checkout: CheckoutDomainModel){
        repo.insertUnfinishedCheckoutModel(checkout)
    }
}