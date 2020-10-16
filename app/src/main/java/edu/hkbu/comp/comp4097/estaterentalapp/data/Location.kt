package edu.hkbu.comp.comp4097.estaterentalapp.data

import android.location.Address
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Location (
    @PrimaryKey
    val estate: String,
    val latitude: String,
    val longitude: String
)
{}