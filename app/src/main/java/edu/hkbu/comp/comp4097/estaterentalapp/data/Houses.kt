package edu.hkbu.comp.comp4097.estaterentalapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Houses(
     val createdAt: String,
     val updatedAt: String,
     @PrimaryKey
     val id: String,
     val property_title: String,
     val image_URL: String,
     val estate: String,
     val bedrooms: String,
     val gross_area: String,
     val expected_tenants: String,
     val rent: String,
     val h_Property: String,
     val occupied: String?)
{}