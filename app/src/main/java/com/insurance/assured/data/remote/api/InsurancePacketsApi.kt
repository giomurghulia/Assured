package com.insurance.assured.data.remote.api

import com.insurance.assured.data.remote.dtos.plans.PlansDto
import retrofit2.Response
import retrofit2.http.GET

interface InsurancePacketsApi {
    @GET("1a454aa4-2277-4cda-a2c1-b459f08f1d05")
    suspend fun getHotPlans(): Response<List<PlansDto>>

    @GET("e4074ca7-75ac-434f-85be-a88156b9a90d")
    suspend fun getHousePlans(): Response<List<PlansDto>>

    @GET("8a38e1cb-e8f3-480d-8c53-55a21bf22ee9")
    suspend fun getLifePlans(): Response<List<PlansDto>>

    @GET("8d32823d-24f7-4d19-9bd1-3d03719f4893")
    suspend fun getVehiclePlans(): Response<List<PlansDto>>

    @GET("b0acc54d-9835-4f61-a871-1d7634e09a80")
    suspend fun getPetPlans(): Response<List<PlansDto>>

    @GET("91004b5f-9420-4281-b35d-66176bb61c44")
    suspend fun checkConnection()
}