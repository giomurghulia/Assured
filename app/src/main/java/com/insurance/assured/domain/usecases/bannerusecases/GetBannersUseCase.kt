package com.insurance.assured.domain.usecases.bannerusecases

import com.insurance.assured.domain.repositorys.BannersRepository
import javax.inject.Inject

class GetBannersUseCase @Inject constructor(private val bannersRepository: BannersRepository) {

    fun invoke(refresh: Boolean = false) = bannersRepository.getBanners(refresh)

}