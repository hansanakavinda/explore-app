package com.example.explore.core.domain

import androidx.paging.PagingData
import com.example.explore.core.data.repository.CountryRepository
import com.example.explore.core.model.Country
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCountriesUseCase @Inject constructor(
    private val countryRepository: CountryRepository
) {
    operator fun invoke(query: String = ""): Flow<PagingData<Country>> {
        return countryRepository.getCountries(query)
    }
}
