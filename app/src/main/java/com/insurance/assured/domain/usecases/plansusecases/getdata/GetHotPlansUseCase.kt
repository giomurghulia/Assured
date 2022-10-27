package com.insurance.assured.domain.usecases.plansusecases.getdata

import com.insurance.assured.domain.repositorys.PlansRepository
import javax.inject.Inject

class GetHotPlansUseCase @Inject constructor(private val repo: PlansRepository) {
    suspend operator fun invoke() = repo.getHotInsurances()
}