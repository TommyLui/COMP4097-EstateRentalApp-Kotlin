package edu.hkbu.comp.comp4097.estaterentalapp.data

import androidx.room.*
@Dao
interface myRentalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(myRentalHouses: myRentalHouses)

    @Query("Select * from myRentalHouses")
    suspend fun findAllRecord(): List<myRentalHouses>

    @Delete
    suspend fun delete(vararg myRentalHouses: myRentalHouses)

    @Update
    suspend fun update(myRentalHouses: myRentalHouses)
}