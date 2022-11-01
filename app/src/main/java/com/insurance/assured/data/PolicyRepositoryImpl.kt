package com.insurance.assured.data

import com.insurance.assured.domain.models.user_policy.UserPolicyModel
import com.insurance.assured.domain.repositorys.PolicyRepository
import com.insurance.assured.data.remote.api.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PolicyRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : PolicyRepository {

    override fun getPolicy(refresh: Boolean): Flow<List<UserPolicyModel>> = flow {
        val response = apiService.getUserPolicy()
        if (response.isSuccessful) {
            emit(response.body()!!)
        }
    }
}