package com.insurance.assured.domain.repositorys

import com.insurance.assured.common.resource.Resource
import com.insurance.assured.domain.models.plans.*

interface PlansRepository {
    suspend fun getLifeInsurances(forceReset: Boolean): Resource<List<PlansModel<LifeSpec>>>
    suspend fun getHouseInsurances(forceReset: Boolean): Resource<List<PlansModel<HouseSpecs>>>
    suspend fun getHotInsurances(): Resource<List<PlansModel<*>>>
    suspend fun getPetInsurances(forceReset: Boolean): Resource<List<PlansModel<PetSpec>>>
    suspend fun getVehicleInsurances(forceReset: Boolean): Resource<List<PlansModel<VehicleSpecs>>>
}