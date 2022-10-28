package com.insurance.assured.networking

import com.insurance.assured.domain.models.banner.BannersModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("f3b0efec-3067-4f93-af5f-825f6b4ac8f9")
    suspend fun getBanner(): Response<List<BannersModel>>
}