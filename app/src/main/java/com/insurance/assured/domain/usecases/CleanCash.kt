package com.insurance.assured.domain.usecases

import com.insurance.assured.domain.usecases.policyusecases.CleanUserDataUseCase
import javax.inject.Inject

class CleanCash @Inject constructor(private val cleanUserDataUseCase: CleanUserDataUseCase) {

    fun invoke() {
        cleanUserDataUseCase.invoke()
    }
}