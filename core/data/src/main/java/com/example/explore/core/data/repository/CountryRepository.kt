package com.example.explore.core.data.repository

import com.example.explore.core.model.Country
import kotlinx.coroutines.flow.Flow

interface CountryRepository {
    fun getCountries(): Flow<List<Country>>
    fun getCountry(code: String): Flow<Country>
}
