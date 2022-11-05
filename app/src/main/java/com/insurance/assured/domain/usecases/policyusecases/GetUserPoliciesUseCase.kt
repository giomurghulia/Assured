package com.insurance.assured.domain.usecases.policyusecases

import com.insurance.assured.domain.repositorys.UserDataRepository
import com.insurance.assured.domain.repositorys.UserPoliciesRepository
import javax.inject.Inject

class GetUserPoliciesUseCase @Inject constructor(private val userPoliciesRepository: UserPoliciesRepository) {

    fun invoke(refresh: Boolean = false) = userPoliciesRepository.getUserPolicies(refresh)

    fun getPolicyById(id: String) = userPoliciesRepository.getUserPolicyById(id)

}