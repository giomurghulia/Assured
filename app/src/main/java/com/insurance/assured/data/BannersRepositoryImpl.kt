package com.insurance.assured.data

import com.insurance.assured.domain.models.banner.BannersModel
import com.insurance.assured.domain.repositorys.BannersRepository
import com.insurance.assured.data.remote.api.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class BannersRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : BannersRepository {

    private var banners: List<BannersModel> = emptyList()

    override fun getBanners(refresh: Boolean): Flow<List<BannersModel>> = flow {
        if (!refresh && banners.isNotEmpty()) {
            emit(banners)
        } else {
            banners = emptyList()

            val response = apiService.getBanner()
            if (response.isSuccessful) {
                banners = response.body()!!
                emit(banners)
            }
        }

    }
}