package com.insurance.assured.di


import com.insurance.assured.data.BannersRepositoryImpl
import com.insurance.assured.domain.repositorys.BannersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BannersRepositoryModule {

    @Binds
    abstract fun bindBannersRepository(bannersRepositoryImpl: BannersRepositoryImpl): BannersRepository
}