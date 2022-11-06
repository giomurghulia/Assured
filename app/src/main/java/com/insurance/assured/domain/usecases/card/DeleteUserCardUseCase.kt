package com.insurance.assured.domain.usecases.card

import com.insurance.assured.domain.repositorys.CardRepository
import javax.inject.Inject

class DeleteUserCardUseCase @Inject constructor(private val cardRepository: CardRepository) {

    suspend fun invoke(userId: String, cardToken: String) =
        cardRepository.deleteCardById(userId, cardToken)

}