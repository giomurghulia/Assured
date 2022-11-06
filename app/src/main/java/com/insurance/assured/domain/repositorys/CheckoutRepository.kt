package com.insurance.assured.domain.repositorys

import com.insurance.assured.domain.models.checkout.CheckoutDomainModel

interface CheckoutRepository {
    suspend fun getUnfinishedCheckouts(): List<CheckoutDomainModel>

    suspend fun insertUnfinishedCheckoutModel(model: CheckoutDomainModel)

    suspend fun deleteUnfinishedCheckoutModel(id: Int)

    suspend fun insertFinishedCheckout(model: CheckoutDomainModel, token: String): Boolean

    suspend fun getMyInsurances(token: String): List<CheckoutDomainModel>
}