package com.example.explore.core.domain

import com.example.explore.core.data.repository.CountryRepository
import com.example.explore.core.model.Country
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCountryUseCase @Inject constructor(
    private val countryRepository: CountryRepository
) {
    operator fun invoke(code: String): Flow<Country> {
        return countryRepository.getCountry(code)
    }
}
