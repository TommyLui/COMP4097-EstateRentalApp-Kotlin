package edu.hkbu.comp.comp4097.estaterentalapp.data

import androidx.room.*
@Dao
interface HousesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(houses: Houses)
    @Query("Select * from houses")
    suspend fun findAllHouses(): List<Houses>
//    @Query("Select * from houses where ")
//    suspend fun findAllBookmarkedEvents(): List<Houses>
    @Delete
    suspend fun delete(vararg houses: Houses)
    @Update
    suspend fun update(houses: Houses)
}