package com.insurance.assured.data.remote.api

import com.insurance.assured.domain.models.banner.BannersModel
import com.insurance.assured.domain.models.userpolicy.UserDataModel
import com.insurance.assured.domain.models.userpolicy.UserPolicyModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("ee312f89-4900-4d16-8f10-e32f24c9e1fd")
    suspend fun getBanner(): Response<List<BannersModel>>

    @GET("af3a694a-65d0-407f-bf19-4dfcdf041392")
    suspend fun getUserData(): Response<UserDataModel>
    @GET("482d7d58-03a2-4f5b-b558-f26c4d2fe057")
    suspend fun getEmptyUserData(): Response<UserDataModel>

    @GET("3f6c578b-9264-4032-b850-17aa23d71363")
    suspend fun getUserPolicies(): Response<List<UserPolicyModel>>

    @GET("9017af9a-b937-489b-89bb-c6d79d837ae1")
    suspend fun getEmptyList(): Response<List<UserPolicyModel>>
}