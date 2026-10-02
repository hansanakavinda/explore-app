package com.example.explore.core.domain

import com.example.explore.core.data.repository.CountryRepository
import com.example.explore.core.model.Country
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCountriesUseCase @Inject constructor(
    private val countryRepository: CountryRepository
) {
    operator fun invoke(query: String = ""): Flow<List<Country>> {
        return countryRepository.getCountries().map { countries ->
            if (query.isBlank()) {
                countries
            } else {
                countries.filter {
                    it.name.contains(query, ignoreCase = true)
                }
            }
        }
    }
}
