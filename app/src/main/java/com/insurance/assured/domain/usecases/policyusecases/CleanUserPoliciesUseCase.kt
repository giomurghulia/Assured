package com.insurance.assured.domain.usecases.policyusecases

import com.insurance.assured.domain.repositorys.UserDataRepository
import com.insurance.assured.domain.repositorys.UserPoliciesRepository
import javax.inject.Inject

class CleanUserPoliciesUseCase @Inject constructor(private val userPoliciesRepository: UserPoliciesRepository) {

    fun invoke() = userPoliciesRepository.clearData()
}