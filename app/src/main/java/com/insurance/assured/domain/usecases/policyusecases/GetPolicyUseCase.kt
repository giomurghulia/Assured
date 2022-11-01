package com.insurance.assured.domain.usecases.policyusecases

import com.insurance.assured.domain.repositorys.PolicyRepository
import javax.inject.Inject

class GetPolicyUseCase @Inject constructor(private val userPolicyRepository: PolicyRepository) {

    fun invoke(refresh: Boolean = false) = userPolicyRepository.getPolicy(refresh)

}