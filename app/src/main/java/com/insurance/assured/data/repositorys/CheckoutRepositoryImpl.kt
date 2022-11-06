package com.insurance.assured.data.repositorys

import com.insurance.assured.data.local.database.Dao
import com.insurance.assured.data.mappers.toDomainModel
import com.insurance.assured.data.mappers.toPurchasedCheckout
import com.insurance.assured.data.mappers.toUnfinishedCheckout
import com.insurance.assured.domain.models.checkout.CheckoutDomainModel
import com.insurance.assured.domain.repositorys.CheckoutRepository
import javax.inject.Inject

class CheckoutRepositoryImpl @Inject constructor(private val dao: Dao) : CheckoutRepository {
    override suspend fun getUnfinishedCheckouts(): List<CheckoutDomainModel> =
        dao.getUnfinishedCheckouts().map { it.toDomainModel() }


    override suspend fun insertUnfinishedCheckoutModel(model: CheckoutDomainModel) {
        dao.insertUnfinishedCheckout(model.toUnfinishedCheckout())
    }

    override suspend fun deleteUnfinishedCheckoutModel(id: Int) {
        dao.deleteUnfinishedCheckout(id)
    }

    override suspend fun insertFinishedCheckout(
        model: CheckoutDomainModel,
        token: String
    ) = if (dao.getPurchase(model.id, token).isEmpty()) {
        dao.insertNewPurchase(model.toPurchasedCheckout(token))
        true
    } else {
        false
    }


    override suspend fun getMyInsurances(token: String): List<CheckoutDomainModel> =
        dao.getPurchasedItems(token).map { it.toDomainModel() }
}