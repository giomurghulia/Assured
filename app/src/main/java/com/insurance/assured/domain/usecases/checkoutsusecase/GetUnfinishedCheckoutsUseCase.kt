package com.insurance.assured.domain.usecases.checkoutsusecase

import com.insurance.assured.domain.repositorys.CheckoutRepository
import javax.inject.Inject

class GetUnfinishedCheckoutsUseCase @Inject constructor(private val repository: CheckoutRepository) {
    suspend operator fun invoke() =
        repository.getUnfinishedCheckouts()
}