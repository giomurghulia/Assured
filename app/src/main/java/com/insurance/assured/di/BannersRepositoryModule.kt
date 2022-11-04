package com.insurance.assured.di


import com.insurance.assured.data.BannersRepositoryImpl
import com.insurance.assured.domain.repositorys.BannersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BannersRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindBannersRepository(bannersRepositoryImpl: BannersRepositoryImpl): BannersRepository
}