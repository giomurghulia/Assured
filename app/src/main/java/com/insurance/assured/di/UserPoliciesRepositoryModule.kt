package com.insurance.assured.di


import com.insurance.assured.data.remote.UserPoliciesRepositoryImpl
import com.insurance.assured.domain.repositorys.UserPoliciesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UserPoliciesRepositoryModule {

    @Binds
    abstract fun bindUserPoliciesRepository(userPoliciesRepositoryImpl: UserPoliciesRepositoryImpl): UserPoliciesRepository
}