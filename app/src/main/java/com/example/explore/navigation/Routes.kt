package com.example.explore.navigation

import kotlinx.serialization.Serializable

@Serializable
data object ListRoute

@Serializable
data class DetailRoute(val countryCode: String)
