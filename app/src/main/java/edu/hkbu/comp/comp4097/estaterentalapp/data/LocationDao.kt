package edu.hkbu.comp.comp4097.estaterentalapp.data

import androidx.room.*
@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(location: Location)

    @Query("Select * from location where estate = :estate")
    suspend fun findCoordinatesByEstate(estate : String): Location

    @Delete
    suspend fun delete(vararg location: Location)

    @Update
    suspend fun update(location: Location)
}
