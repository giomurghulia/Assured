package com.insurance.assured.data.local.database
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query


@Dao
interface Dao {
    @Query("select * from vehicle_insurance order by rating desc")
    suspend fun getVehicleInsurances(): List<VehiclePlansEntity>

    @Query("select * from life_insurance order by rating desc")
    suspend fun getLifeInsurances(): List<LifePlansEntity>

    @Query("select * from pet_insurance order by rating desc")
    suspend fun getPetInsurances(): List<PetPlansEntity>

    @Query("select * from house_insurance order by rating desc")
    suspend fun getHouseInsurances(): List<HousePlansEntity>

    @Query("select * from vehicle_insurance where id = :id")
    suspend fun getVehicleInsuranceById(id: Int): VehiclePlansEntity

    @Query("select * from life_insurance where id = :id")
    suspend fun getLifeInsuranceByID(id: Int): LifePlansEntity

    @Query("select * from pet_insurance where id = :id")
    suspend fun getPetInsuranceById(id: Int): PetPlansEntity

    @Query("select * from house_insurance where id = :id")
    suspend fun getHouseInsuranceById(id: Int): HousePlansEntity


    @Query("delete from life_insurance")
    suspend fun deleteAllLifeInsurances()

    @Query("delete from pet_insurance")
    suspend fun deleteAllPetInsurances()

    @Query("delete from house_insurance")
    suspend fun deleteAllHouseInsurances()

    @Query("delete from vehicle_insurance")
    suspend fun deleteAllVehicleInsurances()




    @Insert(onConflict = REPLACE)
    suspend fun insertAll(vararg plans: LifePlansEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(vararg plans: HousePlansEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(vararg plans: PetPlansEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(vararg plans: VehiclePlansEntity)



}