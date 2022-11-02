package com.insurance.assured.data

import com.insurance.assured.domain.repositorys.UserDataRepository
import com.insurance.assured.data.remote.api.ApiService
import com.insurance.assured.domain.models.user_policy.UserDataModel
import com.insurance.assured.domain.models.user_policy.UserPolicyModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : UserDataRepository {

    private var userData: UserDataModel? = null

    override fun getUserData(refresh: Boolean): Flow<UserDataModel> = flow {
        if (!refresh && userData != null) {
            emit(userData!!)
        } else {
            userData = null
            val response = apiService.getUserData()
            if (response.isSuccessful) {
                userData = response.body()!!
                emit(userData!!)
            }
        }
    }
}