package com.insurance.assured.domain.usecases.card

import com.insurance.assured.domain.repositorys.CardRepository
import javax.inject.Inject

class InsertUserCardUseCase @Inject constructor(private val cardRepository: CardRepository) {

    suspend fun invoke(userId: String, cardToken: String, cardNum: String) =
        cardRepository.insertUserCard(userId, cardToken, cardNum)

}