package com.example.explore.core.domain

import com.example.explore.core.data.repository.CountryRepository
import javax.inject.Inject

class SyncCountriesUseCase @Inject constructor(
    private val countryRepository: CountryRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return countryRepository.syncWithNetwork()
    }
}
