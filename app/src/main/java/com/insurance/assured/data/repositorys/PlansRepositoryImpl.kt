package com.insurance.assured.data.repositorys

import com.insurance.assured.common.enums.InsuranceCategory
import com.insurance.assured.common.enums.InsuranceCategory.Companion.insuranceCategory
import com.insurance.assured.common.resource.Resource
import com.insurance.assured.data.local.database.Dao
import com.insurance.assured.data.mappers.*
import com.insurance.assured.data.remote.api.InsurancePacketsApi
import com.insurance.assured.domain.models.plans.*
import com.insurance.assured.domain.repositorys.PlansRepository
import javax.inject.Inject

class PlansRepositoryImpl @Inject constructor(
    private val dao: Dao,
    private val api: InsurancePacketsApi
) : PlansRepository {
    override suspend fun getLifeInsurances(): Resource<List<PlansModel<LifeSpec>>> =
        try {
            val response = api.getLifePlans()
            if (response.isSuccessful) {
                dao.insertAll(*(response.body()!!.map { it.toLifeEntity() }.toTypedArray()))
                Resource.Success(dao.getLifeInsurances().map { it.toDomain() })
            } else {
                Resource.Error(
                    response.errorBody().toString(),
                    dao.getLifeInsurances().map { it.toDomain() })
            }
        } catch (e: Exception) {
            Resource.Error(
                e.toString(),
                dao.getLifeInsurances().map { it.toDomain() })
        }


    override suspend fun getHouseInsurances(): Resource<List<PlansModel<HouseSpecs>>> =
        try {
            val response = api.getHotPlans()
            if (response.isSuccessful) {
                dao.insertAll(*(response.body()!!.map { it.toHouseEntity() }.toTypedArray()))
                Resource.Success(dao.getHouseInsurances().map { it.toDomain() })
            } else {
                Resource.Error(
                    response.errorBody().toString(),
                    dao.getHouseInsurances().map { it.toDomain() })
            }
        } catch (e: Exception) {
            Resource.Error(
                e.toString(),
                dao.getHouseInsurances().map { it.toDomain() })
        }

    override suspend fun getHotInsurances(): Resource<List<PlansModel<*>>> {
        try {
            val response = api.getHotPlans()
            if (response.isSuccessful) {
                val body = response.body()!!
                body.forEach {
                    when (it.type!!.insuranceCategory()) {
                        InsuranceCategory.HOUSE -> dao.insertAll(it.toHouseEntity())
                        InsuranceCategory.PET -> dao.insertAll(it.toPetEntity())
                        InsuranceCategory.VEHICLE -> dao.insertAll(it.toVehicleEntity())
                        InsuranceCategory.LIFE -> dao.insertAll(it.toLifeEntity())
                        else -> {}
                    }
                }
                return Resource.Success(body.toDomainHotList())
            } else {
                return Resource.Error(
                    response.errorBody().toString(),
                    dao.getLifeInsurances().map { it.toDomain() })
            }
        } catch (e: Exception) {
            return Resource.Error(
                e.toString(),
                dao.getLifeInsurances().map { it.toDomain() })
        }
    }

    override suspend fun getPetInsurances(): Resource<List<PlansModel<PetSpec>>> =
        try {
            val response = api.getPetPlans()
            if (response.isSuccessful) {
                dao.insertAll(*(response.body()!!.map { it.toPetEntity() }.toTypedArray()))
                Resource.Success(dao.getPetInsurances().map { it.toDomain() })
            } else {
                Resource.Error(
                    response.errorBody().toString(),
                    dao.getPetInsurances().map { it.toDomain() })
            }
        } catch (e: Exception) {
            Resource.Error(
                e.toString(),
                dao.getPetInsurances().map { it.toDomain() })
        }

    override suspend fun getVehicleInsurances(): Resource<List<PlansModel<VehicleSpecs>>> =
        try {
            val response = api.getVehiclePlans()
            if (response.isSuccessful) {
                dao.insertAll(*(response.body()!!.map { it.toLifeEntity() }.toTypedArray()))
                Resource.Success(dao.getVehicleInsurances().map { it.toDomain() })
            } else {
                Resource.Error(
                    response.errorBody().toString(),
                    dao.getVehicleInsurances().map { it.toDomain() })
            }
        } catch (e: Exception) {
            Resource.Error(
                e.toString(),
                dao.getVehicleInsurances().map { it.toDomain() })
        }
}