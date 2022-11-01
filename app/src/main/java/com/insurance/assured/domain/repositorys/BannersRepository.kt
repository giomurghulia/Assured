package com.insurance.assured.domain.repositorys

import com.insurance.assured.domain.models.banner.BannersModel
import kotlinx.coroutines.flow.Flow


interface BannersRepository {

    fun getBanners(refresh: Boolean): Flow<List<BannersModel>>

}