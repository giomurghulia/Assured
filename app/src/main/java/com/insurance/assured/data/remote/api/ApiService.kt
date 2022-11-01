package com.insurance.assured.data.remote.api

import com.insurance.assured.domain.models.banner.BannersModel
import com.insurance.assured.domain.models.user_policy.UserPolicyModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("0de6b320-2f0f-4b79-a63b-71c3476274a3")
    suspend fun getBanner(): Response<List<BannersModel>>

    @GET("bcba914d-946c-49e5-ab8a-495c7a8ceb4f")
    suspend fun getUserPolicy(): Response<List<UserPolicyModel>>
}