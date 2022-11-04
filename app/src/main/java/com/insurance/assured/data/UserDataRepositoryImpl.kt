package com.insurance.assured.data

import com.insurance.assured.domain.repositorys.UserDataRepository
import com.insurance.assured.data.remote.api.ApiService
import com.insurance.assured.domain.models.userpolicy.UserDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : UserDataRepository {

    private var userData: AtomicReference<UserDataModel?> = AtomicReference(null)

    override fun getUserData(refresh: Boolean): Flow<UserDataModel> = flow {
        if (!refresh && userData.get() != null) {
            emit(userData.get()!!)
        } else {
            val response = apiService.getUserData()
            if (response.isSuccessful) {
                userData.set(response.body()!!)
                emit(userData.get()!!)
            }
        }
    }
}