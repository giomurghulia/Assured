package com.insurance.assured.data.repositorys

import com.insurance.assured.data.local.database.CardEntity
import com.insurance.assured.data.local.database.Dao
import com.insurance.assured.domain.models.card.CardModel
import com.insurance.assured.domain.repositorys.CardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(private val dao: Dao) : CardRepository {
    override suspend fun insertUserCard(
        userId: String,
        cardToken: String,
        cardNum: String
    ): Boolean {
        dao.insertCard(CardEntity(userId, cardToken, "visa", cardNum))

        val card = dao.getCard(userId).firstOrNull {
            it.cardToken == cardToken
        }
        return card != null
    }

    override suspend fun getUserCard(userId: String): Flow<List<CardModel>> = flow {
        val userCardEntity = dao.getCard(userId)
        val cards = userCardEntity.map {
            CardModel(it.userId, it.cardLastNum, it.cardType, it.cardToken)
        }
        emit(cards)
    }

    override suspend fun deleteCardById(userId: String, cardToken: String): Boolean {
        dao.deleteCard(userId, cardToken)

        val card = dao.getCard(userId).firstOrNull {
            it.cardToken == cardToken
        }

        return card == null
    }
}