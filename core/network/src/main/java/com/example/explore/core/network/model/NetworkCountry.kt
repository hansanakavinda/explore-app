package com.example.explore.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkCountry(
    val cca3: String,
    val name: NetworkName,
    val capital: List<String>? = null,
    val region: String,
    val subregion: String? = null,
    val population: Long,
    val area: Double,
    val flags: NetworkFlags,
    val currencies: Map<String, NetworkCurrency>? = null,
    val languages: Map<String, String>? = null,
    val timezones: List<String>
)

@Serializable
data class NetworkName(
    val common: String
)

@Serializable
data class NetworkFlags(
    val png: String
)

@Serializable
data class NetworkCurrency(
    val name: String,
    val symbol: String? = null
)
