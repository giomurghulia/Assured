package com.insurance.assured.domain.repositorys

import com.insurance.assured.domain.models.userpolicy.UserDataModel
import kotlinx.coroutines.flow.Flow


interface UserDataRepository {
    fun getUserData(refresh: Boolean): Flow<UserDataModel>
    fun clearUserData()
}