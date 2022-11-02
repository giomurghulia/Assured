package com.insurance.assured.data

import com.insurance.assured.domain.repositorys.UserDataRepository
import com.insurance.assured.data.remote.api.ApiService
import com.insurance.assured.domain.models.user_policy.UserDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : UserDataRepository {

    override fun getUserData(refresh: Boolean): Flow<UserDataModel> = flow {
        val response = apiService.getUserData()
        if (response.isSuccessful) {
            emit(response.body()!!)
        }
    }
}