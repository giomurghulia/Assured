package com.insurance.assured.data

import com.insurance.assured.data.remote.api.ApiService
import com.insurance.assured.domain.models.userpolicy.UserPolicyModel
import com.insurance.assured.domain.repositorys.UserPoliciesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
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


    override fun getUserPolicyById(id: String): Flow<UserPolicyModel?> {
        return getUserPolicies(false)
            .map { policies ->
                policies.firstOrNull {
                    id == it.id
                }
            }
    }
}