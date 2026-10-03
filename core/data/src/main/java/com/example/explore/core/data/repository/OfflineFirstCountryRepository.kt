package com.example.explore.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.explore.core.data.model.asEntity
import com.example.explore.core.data.model.asExternalModel
import com.example.explore.core.database.dao.CountryDao
import com.example.explore.core.model.Country
import com.example.explore.core.network.model.NetworkCountry
import com.example.explore.core.network.retrofit.CountryNetworkApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineFirstCountryRepository @Inject constructor(
    private val countryDao: CountryDao,
    private val networkApi: CountryNetworkApi
) : CountryRepository {

    override fun getCountries(query: String): Flow<PagingData<Country>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                countryDao.getCountries(query)
            }
        ).flow.map { pagingData ->
            pagingData.map { it.asExternalModel() }
        }
    }

    override fun getCountry(code: String): Flow<Country> {
        return countryDao.getCountry(code).map { it.asExternalModel() }
    }

    override suspend fun syncWithNetwork(): Result<Unit> {
        return try {
            val allNetworkCountries = mutableListOf<NetworkCountry>()
            var offset = 0
            while (true) {
                val response = networkApi.getAllCountries(limit = 100, offset = offset)
                val countries = response.data?.objects ?: emptyList()
                if (countries.isEmpty()) break
                allNetworkCountries.addAll(countries)
                if (countries.size < 100) break
                offset += countries.size
            }

            if (allNetworkCountries.isNotEmpty()) {
                val entities = allNetworkCountries.map { it.asEntity() }
                countryDao.clearAll()
                countryDao.insertAll(entities)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}
