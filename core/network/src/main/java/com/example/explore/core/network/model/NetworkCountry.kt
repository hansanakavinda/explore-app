package com.example.explore.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkResponse(
    val data: NetworkData? = null
)

@Serializable
data class NetworkData(
    val objects: List<NetworkCountry>? = null
)

@Serializable
data class NetworkCountry(
    val names: NetworkName? = null,
    val codes: NetworkCodes? = null,
    val capitals: List<NetworkCapital>? = null,
    val region: String? = null,
    val subregion: String? = null,
    val population: Long? = null,
    val area: NetworkArea? = null,
    val flag: NetworkFlag? = null,
    val currencies: List<NetworkCurrency>? = null,
    val languages: List<NetworkLanguage>? = null,
    val timezones: List<String>? = null
)

@Serializable
data class NetworkName(
    val common: String? = null
)

@Serializable
data class NetworkCodes(
    val alpha_3: String? = null
)

@Serializable
data class NetworkCapital(
    val name: String? = null
)

@Serializable
data class NetworkFlag(
    val url_png: String? = null
)

@Serializable
data class NetworkArea(
    val kilometers: Double? = null
)

@Serializable
data class NetworkCurrency(
    val name: String? = null
)

@Serializable
data class NetworkLanguage(
    val name: String? = null
)
