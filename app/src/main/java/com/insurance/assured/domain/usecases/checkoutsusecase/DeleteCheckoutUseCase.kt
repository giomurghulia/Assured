package com.insurance.assured.domain.usecases.checkoutsusecase

import com.insurance.assured.domain.repositorys.CheckoutRepository
import javax.inject.Inject

class DeleteCheckoutUseCase @Inject constructor(private val repo: CheckoutRepository) {
    suspend operator fun invoke(id: Int) {
        repo.deleteUnfinishedCheckoutModel(id)
    }
}