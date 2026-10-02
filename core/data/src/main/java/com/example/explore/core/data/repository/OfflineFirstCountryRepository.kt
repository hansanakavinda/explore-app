package com.example.explore.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.explore.core.data.model.asEntity
import com.example.explore.core.data.model.asExternalModel
import com.example.explore.core.database.dao.CountryDao
import com.example.explore.core.model.Country
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
            val networkCountries = networkApi.getAllCountries()
            val entities = networkCountries.map { it.asEntity() }
            
            countryDao.clearAll()
            countryDao.insertAll(entities)
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
