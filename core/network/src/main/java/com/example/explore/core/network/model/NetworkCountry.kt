package com.example.explore.core.network.model

// Stub for Day 4 network implementation
data class NetworkCountry(
    val cca3: String,
    val name: NetworkName
)

data class NetworkName(val common: String)

// Stub mapper
// fun NetworkCountry.asEntity() = CountryEntity(...)
