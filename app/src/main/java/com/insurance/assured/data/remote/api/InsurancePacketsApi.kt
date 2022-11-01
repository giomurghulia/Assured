package com.insurance.assured.data.remote.api

import com.insurance.assured.data.remote.dtos.plans.PlansDto
import retrofit2.Response
import retrofit2.http.GET

interface InsurancePacketsApi {
    @GET("d3637ad1-d713-4b4e-98f9-b6d9dd7178d0")
    suspend fun getHotPlans(): Response<List<PlansDto>>

    @GET("8c273ba8-6fa1-47e3-b614-037551c2307d")
    suspend fun getHousePlans(): Response<List<PlansDto>>

    @GET("1b5d9550-effb-4998-bb0f-79eb0df94ad4")
    suspend fun getLifePlans(): Response<List<PlansDto>>

    @GET("3e0aa689-9e82-4557-a692-e3e5eb5b0516")
    suspend fun getVehiclePlans(): Response<List<PlansDto>>

    @GET("d71e368c-2b64-45ee-8c01-874036d31c37")
    suspend fun getPetPlans(): Response<List<PlansDto>>
}