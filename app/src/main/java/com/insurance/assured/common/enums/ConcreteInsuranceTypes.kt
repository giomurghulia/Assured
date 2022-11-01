package com.insurance.assured.common.enums

enum class HouseInsuranceType {
    APARTMENT {
        override fun toString() = "apartment"
    },
    HOUSE {
        override fun toString() = "house"
    },
    COUNTRY_HOUSE {
        override fun toString() = "country house"
    },
    DEFAULT;

    companion object {
        fun String.toHouseInsuranceType() = when (this) {
            "apartment" -> APARTMENT
            "house" -> HOUSE
            "country house" -> COUNTRY_HOUSE
            else -> DEFAULT
        }
    }
}

enum class VehicleInsuranceType {
    CAR {
        override fun toString() = "car"
    },
    MOTO {
        override fun toString() = "moto"
    },
    TRUCK {
        override fun toString() = "truck"
    },
    DEFAULT;

    companion object {
        fun String.toVehicleInsuranceType() = when (this) {
            "moto" -> MOTO
            "car" -> CAR
            "TRUCK" -> TRUCK
            else -> DEFAULT
        }
    }
}