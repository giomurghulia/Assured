package com.insurance.assured.data.local.database

import androidx.room.*


@androidx.room.Database(
    entities = [
        LifePlansEntity::class,
        PetPlansEntity::class,
        HousePlansEntity::class,
        VehiclePlansEntity::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class Database: RoomDatabase() {
    abstract fun getDao(): Dao
}