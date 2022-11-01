package com.insurance.assured.common.enums

import androidx.annotation.DrawableRes
import com.insurance.assured.R

enum class InsuranceCategory {
    HOT_OFFERS {
        override val icon: Int
            get() = R.drawable.life

        override fun toString() = "Hot Offers"
    },
    HEALTH {
        override val icon: Int
            get() = R.drawable.life

        override fun toString() = "Life"
    },
    HOUSE {
        override val icon: Int
            get() = R.drawable.ic_baseline_house_24

        override fun toString() = "House"
    },
    VEHICLE {
        override val icon: Int
            get() = R.drawable.vehicle

        override fun toString() = "Vehicle"
    },
    PET {
        override val icon: Int
            get() = R.drawable.ic_baseline_pets_24


        override fun toString() = "Pet"
    },
    DEFAULT {
        override fun toString() = "Something went wrong"
    };

    @DrawableRes
    open val icon: Int = R.drawable.filter

    companion object {
        fun String.insuranceCategory() = when (this) {
            "vehicle" -> VEHICLE
            "pet" -> PET
            "health" -> HEALTH
            "house" -> HOUSE
            else -> VehicleInsuranceType.DEFAULT
        }
    }
}