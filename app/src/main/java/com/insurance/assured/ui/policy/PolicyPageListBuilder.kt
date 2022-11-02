package com.insurance.assured.ui.policy

import com.insurance.assured.common.resource.Result
import com.insurance.assured.common.resource.onError
import com.insurance.assured.common.resource.onLoading
import com.insurance.assured.common.resource.onSuccess
import com.insurance.assured.domain.models.user_policy.UserDataModel
import com.insurance.assured.domain.models.user_policy.UserPolicyModel
import com.insurance.assured.ui.home.Banner
import com.insurance.assured.ui.home.HomeListItem
import javax.inject.Inject

class PolicyPageListBuilder @Inject constructor() {

    fun buildList(payload: PolicyPagePayload): List<PolicyListItem> {
        return buildUserData(payload.userData)
            .plus(listOf(PolicyListItem.TitleItem("Your Policy","Access your benefits now")))
            .plus(buildPoliciesList(payload.userPolicies))
    }

    private fun buildUserData(userData: Result<UserDataModel>): List<PolicyListItem> {
        val list = mutableListOf<PolicyListItem>()

        userData.onSuccess { data ->
            list.add(
                PolicyListItem.UserDataItem(
                    data.id,
                    data.full_amount.toString(),
                    data.all_policy_number.toString(),
                    data.health_insurance.toString(),
                    data.house_insurance.toString(),
                    data.vehicle_insurance.toString(),
                    data.pet_insurance.toString()
                )
            )

        }

        userData.onError {
            list.add(PolicyListItem.ErrorUserDataItem)
        }

        userData.onLoading {
            list.add(PolicyListItem.ErrorUserDataItem)
        }

        return list
    }

    private fun buildPoliciesList(userPolicies: Result<List<UserPolicyModel>>): List<PolicyListItem> {
        val list = mutableListOf<PolicyListItem>()

        userPolicies.onSuccess { data ->
            list.addAll(
                data.map {
                    PolicyListItem.PolicyItem(it.id, it.title, it.type, it.banner)
                }
            )
        }

        userPolicies.onError {
            list.add(PolicyListItem.ErrorPolicyItem)
            list.add(PolicyListItem.ErrorPolicyItem)
            list.add(PolicyListItem.ErrorPolicyItem)
            list.add(PolicyListItem.ErrorPolicyItem)
            list.add(PolicyListItem.ErrorPolicyItem)
        }

        userPolicies.onLoading {
            list.add(PolicyListItem.ErrorPolicyItem)
            list.add(PolicyListItem.ErrorPolicyItem)
            list.add(PolicyListItem.ErrorPolicyItem)
            list.add(PolicyListItem.ErrorPolicyItem)
            list.add(PolicyListItem.ErrorPolicyItem)
        }

        return list
    }

}