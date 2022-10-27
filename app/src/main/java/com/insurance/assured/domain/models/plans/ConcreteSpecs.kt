package com.insurance.assured.domain.models.plans

import com.insurance.assured.common.enums.HouseInsuranceType
import com.insurance.assured.common.enums.VehicleInsuranceType

data class HouseSpecs(val type: HouseInsuranceType): Spec
data class VehicleSpecs(val type: VehicleInsuranceType): Spec
data class PetSpec(val pet: String): Spec
data class LifeSpec(val personCount: Int): Spec

interface Spec
