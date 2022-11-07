package com.insurance.assured.domain.usecases.checkoutsusecase

import com.insurance.assured.domain.repositorys.PlansRepository
import javax.inject.Inject

class CheckConnectionUseCase @Inject constructor(private val repo: PlansRepository) {
    suspend operator fun invoke() = repo.checkConnection()
}