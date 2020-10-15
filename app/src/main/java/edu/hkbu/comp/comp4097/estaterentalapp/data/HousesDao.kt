package edu.hkbu.comp.comp4097.estaterentalapp.data

import androidx.room.*
@Dao
interface HousesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(houses: Houses)

    @Query("Select * from houses")
    suspend fun findAllHouses(): List<Houses>

    @Query("Select * from houses where estate = :estate")
    suspend fun findAllHousesByEstate(estate: String): List<Houses>

    @Query("Select * from houses where bedrooms <= :roomNum")
    suspend fun findAllHousesByRoomsSmallerThan(roomNum: Int): List<Houses>

    @Query("Select * from houses where bedrooms >= :roomNum")
    suspend fun findAllHousesByRoomsLargerThan(roomNum : Int): List<Houses>

    @Delete
    suspend fun delete(vararg houses: Houses)

    @Update
    suspend fun update(houses: Houses)
}