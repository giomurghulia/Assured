package com.insurance.assured.domain.repositorys

import com.insurance.assured.domain.models.user_policy.UserPolicyModel
import kotlinx.coroutines.flow.Flow



interface PolicyRepository {
    fun getPolicy(refresh: Boolean): Flow<List<UserPolicyModel>>
}