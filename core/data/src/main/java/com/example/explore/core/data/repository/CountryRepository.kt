package com.example.explore.core.data.repository

import androidx.paging.PagingData
import com.example.explore.core.model.Country
import kotlinx.coroutines.flow.Flow

interface CountryRepository {
    fun getCountries(query: String): Flow<PagingData<Country>>
    fun getCountry(code: String): Flow<Country>
    suspend fun syncWithNetwork(): Result<Unit>
}
