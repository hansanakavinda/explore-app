package com.example.explore.core.data.repository

import com.example.explore.core.database.dao.CountryDao
import com.example.explore.core.database.model.CountryEntity
import com.example.explore.core.network.model.NetworkArea
import com.example.explore.core.network.model.NetworkCodes
import com.example.explore.core.network.model.NetworkCountry
import com.example.explore.core.network.model.NetworkData
import com.example.explore.core.network.model.NetworkFlag
import com.example.explore.core.network.model.NetworkName
import com.example.explore.core.network.model.NetworkResponse
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
        val networkData = NetworkResponse(
            data = NetworkData(
                objects = listOf(
                    NetworkCountry(
                        codes = NetworkCodes("LKA"),
                        names = NetworkName("Sri Lanka"),
                        region = "Asia",
                        population = 22000000,
                        area = NetworkArea(65610.0),
                        flag = NetworkFlag("url"),
                        timezones = listOf("UTC+05:30")
                    )
                )
            )
        )
        coEvery { mockApi.getAllCountries(any(), any()) } returns networkData

        val result = repository.syncWithNetwork()

        assertTrue(result.isSuccess)
        coVerify { mockDao.clearAll() }
        coVerify { mockDao.insertAll(any()) }
    }

    @Test
    fun `syncWithNetwork failure returns failure result`() = runTest {
        coEvery { mockApi.getAllCountries(any(), any()) } throws RuntimeException("Network Error")

        val result = repository.syncWithNetwork()

        assertTrue(result.isFailure)
        coVerify(exactly = 0) { mockDao.clearAll() }
    }
}
