package com.insurance.assured.di

import com.insurance.assured.data.repositorys.CardRepositoryImpl
import com.insurance.assured.domain.repositorys.CardRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class CardRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCardRepository(cardRepositoryImpl: CardRepositoryImpl): CardRepository
}