package com.insurance.assured.networking

import com.insurance.assured.domain.models.banner.BannersModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("0de6b320-2f0f-4b79-a63b-71c3476274a3")
    suspend fun getBanner(): Response<List<BannersModel>>
}