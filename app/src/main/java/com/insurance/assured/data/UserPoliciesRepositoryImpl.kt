package com.insurance.assured.data

import com.insurance.assured.data.remote.api.ApiService
import com.insurance.assured.domain.models.user_policy.UserPolicyModel
import com.insurance.assured.domain.repositorys.UserPoliciesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPoliciesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : UserPoliciesRepository {

    private var policies: List<UserPolicyModel> = emptyList()

    override fun getUserPolicies(refresh: Boolean): Flow<List<UserPolicyModel>> = flow {
        if (!refresh && policies.isNotEmpty()) {
            emit(policies)
        } else {
            val response = apiService.getUserPolicies()
            if (response.isSuccessful) {
                policies = response.body()!!
                emit(policies)
            }
        }
    }
}