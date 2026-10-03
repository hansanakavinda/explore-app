package com.example.explore.core.network.retrofit

import com.example.explore.core.network.model.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CountryNetworkApi {
    @GET("countries/v5")
    suspend fun getAllCountries(
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int = 0
    ): NetworkResponse
}
