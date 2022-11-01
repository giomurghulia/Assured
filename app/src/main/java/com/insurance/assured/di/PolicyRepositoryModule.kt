package com.insurance.assured.di


import com.insurance.assured.data.PolicyRepositoryImpl
import com.insurance.assured.domain.repositorys.PolicyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PolicyRepositoryModule{

    @Binds
    abstract fun bindPolicyRepository(policyRepositoryImpl: PolicyRepositoryImpl): PolicyRepository
}