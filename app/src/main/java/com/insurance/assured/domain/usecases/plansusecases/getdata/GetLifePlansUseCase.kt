package com.insurance.assured.domain.usecases.plansusecases.getdata

import com.insurance.assured.domain.repositorys.PlansRepository
import javax.inject.Inject

class GetLifePlansUseCase @Inject constructor(private val repo: PlansRepository) {
    suspend operator fun invoke(forceReset: Boolean = false) = repo.getLifeInsurances(forceReset)
}