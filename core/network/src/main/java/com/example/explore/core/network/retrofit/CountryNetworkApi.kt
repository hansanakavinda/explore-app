package com.example.explore.core.network.retrofit

import com.example.explore.core.network.model.NetworkResponse
import retrofit2.http.GET

interface CountryNetworkApi {
    @GET("countries/v5")
    suspend fun getAllCountries(): NetworkResponse
}
