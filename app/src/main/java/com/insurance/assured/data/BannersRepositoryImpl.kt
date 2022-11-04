package com.insurance.assured.data

import com.insurance.assured.domain.models.banner.BannersModel
import com.insurance.assured.domain.repositorys.BannersRepository
import com.insurance.assured.data.remote.api.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BannersRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : BannersRepository {

    private var banners: AtomicReference<List<BannersModel>> = AtomicReference(emptyList())

    override fun getBanners(refresh: Boolean): Flow<List<BannersModel>> = flow {
        if (!refresh && banners.get().isNotEmpty()) {
            emit(banners.get())
        } else {
            val response = apiService.getBanner()
            if (response.isSuccessful) {
                banners.set(response.body()!!)
                emit(banners.get())
            }
        }
    }
}