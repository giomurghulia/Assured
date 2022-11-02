package com.insurance.assured.data.remote.api

import com.insurance.assured.domain.models.banner.BannersModel
import com.insurance.assured.domain.models.user_policy.UserDataModel
import com.insurance.assured.domain.models.user_policy.UserPolicyModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("0de6b320-2f0f-4b79-a63b-71c3476274a3")
    suspend fun getBanner(): Response<List<BannersModel>>

    @GET("af3a694a-65d0-407f-bf19-4dfcdf041392")
    suspend fun getUserData(): Response<UserDataModel>

    @GET("3f6c578b-9264-4032-b850-17aa23d71363")
    suspend fun getUserPolicies(): Response<List<UserPolicyModel>>
}