package com.example.explore.feature.list

import androidx.paging.PagingData
import app.cash.turbine.test
import com.example.explore.core.domain.GetCountriesUseCase
import com.example.explore.core.domain.SyncCountriesUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CountryListViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val mockGetCountriesUseCase: GetCountriesUseCase = mockk()
    private val mockSyncCountriesUseCase: SyncCountriesUseCase = mockk()
    private lateinit var viewModel: CountryListViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { mockGetCountriesUseCase(any()) } returns flowOf(PagingData.empty())
        coEvery { mockSyncCountriesUseCase() } returns Result.success(Unit)
        viewModel = CountryListViewModel(mockGetCountriesUseCase, mockSyncCountriesUseCase)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `query flow emits default and updated queries`() = runTest {
        viewModel.query.test {
            assertEquals("", awaitItem())
            
            viewModel.onQueryChange("Sri Lanka")
            assertEquals("Sri Lanka", awaitItem())
            
            cancelAndIgnoreRemainingEvents()
        }
    }
}
