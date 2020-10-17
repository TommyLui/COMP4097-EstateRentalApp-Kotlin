package edu.hkbu.comp.comp4097.estaterentalapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class myRentalHouses(
     @PrimaryKey
     val json: String
)
{}