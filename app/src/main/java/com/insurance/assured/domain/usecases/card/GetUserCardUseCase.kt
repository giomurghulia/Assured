package com.insurance.assured.domain.usecases.card

import com.insurance.assured.domain.repositorys.CardRepository
import javax.inject.Inject

class GetUserCardUseCase @Inject constructor(private val cardRepository: CardRepository) {

    suspend operator fun invoke(userId: String) = cardRepository.getUserCard(userId)

}