package com.insurance.assured.domain.usecases.policyusecases

import com.insurance.assured.domain.repositorys.UserDataRepository
import com.insurance.assured.domain.repositorys.UserPoliciesRepository
import javax.inject.Inject

class GetPolicyByIdUseCase @Inject constructor(private val userPoliciesRepository: UserPoliciesRepository) {

    fun invoke(id: String) = userPoliciesRepository.getUserPolicyById(id)

}