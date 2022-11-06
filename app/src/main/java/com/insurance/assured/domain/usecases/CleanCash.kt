package com.insurance.assured.domain.usecases

import com.insurance.assured.domain.usecases.policyusecases.CleanUserDataUseCase
import com.insurance.assured.domain.usecases.policyusecases.CleanUserPoliciesUseCase
import javax.inject.Inject

class CleanCash @Inject constructor(
    private val cleanUserDataUseCase: CleanUserDataUseCase,
    private val cleanUserPoliciesUseCase: CleanUserPoliciesUseCase
) {

    fun invoke() {
        cleanUserDataUseCase.invoke()
        cleanUserPoliciesUseCase.invoke()
    }
}