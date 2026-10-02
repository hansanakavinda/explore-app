package com.example.explore.core.data.repository

import com.example.explore.core.database.dao.CountryDao
import com.example.explore.core.database.model.CountryEntity
import com.example.explore.core.network.model.NetworkCountry
import com.example.explore.core.network.model.NetworkFlags
import com.example.explore.core.network.model.NetworkName
import com.example.explore.core.network.retrofit.CountryNetworkApi
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class OfflineFirstCountryRepositoryTest {

    private lateinit var repository: OfflineFirstCountryRepository
    private val mockDao: CountryDao = mockk(relaxed = true)
    private val mockApi: CountryNetworkApi = mockk()

    @Before
    fun setup() {
        repository = OfflineFirstCountryRepository(mockDao, mockApi)
    }

    @Test
    fun `syncWithNetwork success clears and inserts`() = runTest {
        val networkData = listOf(
            NetworkCountry(
                cca3 = "LKA",
                name = NetworkName("Sri Lanka"),
                region = "Asia",
                population = 22000000,
                area = 65610.0,
                flags = NetworkFlags("url"),
                timezones = listOf("UTC+05:30")
            )
        )
        coEvery { mockApi.getAllCountries() } returns networkData

        val result = repository.syncWithNetwork()

        assertTrue(result.isSuccess)
        coVerify { mockDao.clearAll() }
        coVerify { mockDao.insertAll(any()) }
    }

    @Test
    fun `syncWithNetwork failure returns failure result`() = runTest {
        coEvery { mockApi.getAllCountries() } throws RuntimeException("Network Error")

        val result = repository.syncWithNetwork()

        assertTrue(result.isFailure)
        coVerify(exactly = 0) { mockDao.clearAll() }
    }
}
