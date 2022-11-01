package com.insurance.assured.domain.repositorys

import com.insurance.assured.common.resource.Resource
import com.insurance.assured.domain.models.plans.*

interface PlansRepository {
    suspend fun getLifeInsurances(): Resource<List<PlansModel<LifeSpec>>>
    suspend fun getHouseInsurances(): Resource<List<PlansModel<HouseSpecs>>>
    suspend fun getHotInsurances(): Resource<List<PlansModel<Spec>>>
    suspend fun getPetInsurances(): Resource<List<PlansModel<PetSpec>>>
    suspend fun getVehicleInsurances(): Resource<List<PlansModel<VehicleSpecs>>>
}