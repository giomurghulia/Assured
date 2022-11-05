package com.insurance.assured.domain.repositorys

import com.insurance.assured.domain.models.userpolicy.UserPolicyModel
import kotlinx.coroutines.flow.Flow


interface UserPoliciesRepository {
    fun getUserPolicies(refresh: Boolean): Flow<List<UserPolicyModel>>

    fun getUserPolicyById(id: String): Flow<UserPolicyModel>

    fun clearData()
}