package com.insurance.assured.data.remote

import com.insurance.assured.data.remote.api.ApiService
import com.insurance.assured.domain.models.user_policy.UserPolicyModel
import com.insurance.assured.domain.repositorys.UserPoliciesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserPoliciesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : UserPoliciesRepository {

    override fun getUserPolicies(refresh: Boolean): Flow<List<UserPolicyModel>> = flow {
        val response = apiService.getUserPolicies()
        if (response.isSuccessful) {
            emit(response.body()!!)
        }
    }
}