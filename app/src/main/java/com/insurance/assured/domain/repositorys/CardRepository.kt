package com.insurance.assured.domain.repositorys

import com.insurance.assured.domain.models.card.CardModel
import kotlinx.coroutines.flow.Flow

interface CardRepository {
    suspend fun insertUserCard(userId: String, cardToken: String, cardNum: String): Boolean

    suspend fun getUserCard(userId: String): Flow<List<CardModel>>

    suspend fun deleteCardById(userId: String, cardToken: String): Boolean
}