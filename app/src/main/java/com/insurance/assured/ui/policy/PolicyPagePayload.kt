package com.insurance.assured.ui.policy

import com.insurance.assured.common.resource.Result
import com.insurance.assured.domain.models.userpolicy.UserDataModel
import com.insurance.assured.domain.models.userpolicy.UserPolicyModel


data class PolicyPagePayload(
    val userData: Result<UserDataModel> = Result.Loading,
    val userPolicies: Result<List<UserPolicyModel>> = Result.Loading,
)
