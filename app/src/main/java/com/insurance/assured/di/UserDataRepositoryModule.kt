package com.insurance.assured.di


import com.insurance.assured.data.UserDataRepositoryImpl
import com.insurance.assured.domain.repositorys.UserDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserDataRepositoryModule{

    @Binds
    @Singleton
    abstract fun bindUserDataRepository(userDataRepositoryImpl: UserDataRepositoryImpl): UserDataRepository
}