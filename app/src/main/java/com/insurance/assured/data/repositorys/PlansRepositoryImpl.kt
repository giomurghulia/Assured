package com.insurance.assured.data.repositorys

import com.insurance.assured.common.resource.Resource
import com.insurance.assured.domain.models.plans.*
import com.insurance.assured.domain.repositorys.PlansRepository

class PlansRepositoryImpl: PlansRepository {
    override suspend fun getLifeInsurances(): Resource<List<PlansModel<LifeSpec>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getHouseInsurances(): Resource<List<PlansModel<HouseSpecs>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getHotInsurances(): Resource<List<PlansModel<Spec>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPetInsurances(): Resource<List<PlansModel<PetSpec>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getVehicleInsurances(): Resource<List<PlansModel<VehicleSpecs>>> {
        TODO("Not yet implemented")
    }
}