package com.insurance.assured.ui.policy

import com.insurance.assured.common.resource.Result
import com.insurance.assured.domain.models.user_policy.UserDataModel
import com.insurance.assured.domain.models.user_policy.UserPolicyModel


data class PolicyPagePayload(
    val userData: Result<UserDataModel> = Result.Loading,
    val userPolicies: Result<List<UserPolicyModel>> = Result.Loading,
)
