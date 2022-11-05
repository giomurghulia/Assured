package com.insurance.assured.data

import com.insurance.assured.data.remote.api.ApiService
import com.insurance.assured.domain.models.userpolicy.UserPolicyModel
import com.insurance.assured.domain.repositorys.UserPoliciesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPoliciesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : UserPoliciesRepository {

    private var policies: AtomicReference<List<UserPolicyModel>> = AtomicReference(emptyList())

    override fun getUserPolicies(refresh: Boolean): Flow<List<UserPolicyModel>> = flow {
        if (!refresh && policies.get().isNotEmpty()) {
            emit(policies.get())
        } else {
            val response = apiService.getUserPolicies()
            if (response.isSuccessful) {
                policies.set(response.body()!!)
                emit(policies.get())
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

    override fun clearData() {
        policies.set(emptyList())
    }
}