package edu.hkbu.comp.comp4097.estaterentalapp.data

data class AccountInfo(
    val createdAt: String,
    val updatedAt: String,
    val id: String,
    val username: String,
    val password: String,
    val role: String,
    val avatar: String,
    val rent: String
    )
{}