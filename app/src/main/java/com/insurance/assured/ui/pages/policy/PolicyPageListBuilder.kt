package com.insurance.assured.ui.pages.policy

import com.insurance.assured.common.resource.Result
import com.insurance.assured.common.resource.onError
import com.insurance.assured.common.resource.onLoading
import com.insurance.assured.common.resource.onSuccess
import com.insurance.assured.domain.models.userpolicy.UserDataModel
import com.insurance.assured.domain.models.userpolicy.UserPolicyModel
import javax.inject.Inject

class PolicyPageListBuilder @Inject constructor() {

    fun buildList(payload: PolicyPagePayload): List<PolicyListItem> {
        return buildUserData(payload.userData)
            .plus(buildPoliciesList(payload.userPolicies))
            .plus(PolicyListItem.SpaceItem)
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
            list.add(PolicyListItem.ShimmerUserDataItem)
        }

        return list
    }

    private fun buildPoliciesList(userPolicies: Result<List<UserPolicyModel>>): List<PolicyListItem> {
        val list = mutableListOf<PolicyListItem>()

        userPolicies.onSuccess { data ->

            if (data.isNotEmpty()) {
                list.add(PolicyListItem.TitleItem("Your Policy", "Access your benefits now"))
                list.addAll(
                    data.map {
                        PolicyListItem.PolicyItem(it.id, it.title, it.type, it.banner)
                    }
                )
            }else{
                list.add(PolicyListItem.NoPolicyItem)
                list.add(PolicyListItem.CashlessBannerItem)
            }
        }

        userPolicies.onError {
            list.add(PolicyListItem.TitleItem("Your Policy", "Access your benefits now"))

            list.add(PolicyListItem.ErrorPolicyItem)
        }

        userPolicies.onLoading {
            list.add(PolicyListItem.TitleItem("Your Policy", "Access your benefits now"))

            list.add(PolicyListItem.ShimmerPolicyItem)
            list.add(PolicyListItem.ShimmerPolicyItem)
        }

        return list
    }

}