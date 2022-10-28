package com.insurance.assured.data


import android.util.Log
import com.insurance.assured.domain.models.banner.BannersModel
import com.insurance.assured.domain.repositorys.BannersRepository
import com.insurance.assured.networking.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject


class BannersRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : BannersRepository {


    override fun getBanners(refresh: Boolean): Flow<List<BannersModel>> = flow {

        try {
            Log.d("REPO", "before req")
            val response = apiService.getBanner()
            Log.d("REPO", "request")
            if (response.isSuccessful) {
                Log.d("REPO", "EMIT")
                emit(response.body()!!)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}