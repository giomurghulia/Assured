package com.insurance.assured.domain.repositorys

import com.insurance.assured.domain.models.user_policy.UserDataModel
import com.insurance.assured.domain.models.user_policy.UserPolicyModel
import kotlinx.coroutines.flow.Flow


interface UserPoliciesRepository {
    fun getUserPolicies(refresh: Boolean): Flow<List<UserPolicyModel>>
}