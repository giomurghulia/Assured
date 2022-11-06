package com.insurance.assured.domain.usecases.policyusecases

import com.insurance.assured.domain.repositorys.UserDataRepository
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(private val userDataRepository: UserDataRepository) {

    fun invoke(refresh: Boolean = false) = userDataRepository.getUserData(refresh)
}