package com.insurance.assured.domain.usecases.plansusecases.getdata

import com.insurance.assured.domain.repositorys.PlansRepository
import javax.inject.Inject

class GetPetPlansUseCase @Inject constructor(private val repo: PlansRepository) {
    suspend operator fun invoke(forceReset: Boolean = false) = repo.getPetInsurances(forceReset)
}