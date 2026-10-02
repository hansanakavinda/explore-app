package com.example.explore.core.network.retrofit

import com.example.explore.core.network.model.NetworkCountry
import retrofit2.http.GET

interface CountryNetworkApi {
    @GET("v3.1/all")
    suspend fun getAllCountries(): List<NetworkCountry>
}
