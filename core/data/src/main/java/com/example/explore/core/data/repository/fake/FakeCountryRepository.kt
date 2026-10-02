package com.example.explore.core.data.repository.fake

import com.example.explore.core.data.repository.CountryRepository
import com.example.explore.core.model.Country
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeCountryRepository @Inject constructor() : CountryRepository {

    private val fakeCountries = listOf(
        Country("LKA", "Sri Lanka", "Colombo", "Asia", "South Asia", "22 million", "65,610 km²", "", listOf("LKR"), listOf("Sinhala", "Tamil", "English"), listOf("UTC+5:30")),
        Country("JPN", "Japan", "Tokyo", "Asia", "Eastern Asia", "125 million", "377,975 km²", "", listOf("JPY"), listOf("Japanese"), listOf("UTC+9:00")),
        Country("FRA", "France", "Paris", "Europe", "Western Europe", "67 million", "551,695 km²", "", listOf("EUR"), listOf("French"), listOf("UTC+1:00")),
        Country("AUS", "Australia", "Canberra", "Oceania", "Australia and New Zealand", "25 million", "7,692,024 km²", "", listOf("AUD"), listOf("English"), listOf("UTC+8:00 to UTC+10:30")),
        Country("CAN", "Canada", "Ottawa", "Americas", "North America", "38 million", "9,984,670 km²", "", listOf("CAD"), listOf("English", "French"), listOf("UTC-8:00 to UTC-3:30")),
        Country("KOR", "South Korea", "Seoul", "Asia", "Eastern Asia", "51 million", "100,210 km²", "", listOf("KRW"), listOf("Korean"), listOf("UTC+9:00")),
        Country("BRA", "Brazil", "Brasília", "Americas", "South America", "212 million", "8,515,767 km²", "", listOf("BRL"), listOf("Portuguese"), listOf("UTC-5:00 to UTC-2:00")),
        Country("EGY", "Egypt", "Cairo", "Africa", "Northern Africa", "102 million", "1,002,450 km²", "", listOf("EGP"), listOf("Arabic"), listOf("UTC+2:00")),
        Country("ITA", "Italy", "Rome", "Europe", "Southern Europe", "60 million", "301,340 km²", "", listOf("EUR"), listOf("Italian"), listOf("UTC+1:00")),
        Country("IND", "India", "New Delhi", "Asia", "Southern Asia", "1.38 billion", "3,287,263 km²", "", listOf("INR"), listOf("Hindi", "English"), listOf("UTC+5:30"))
    )

    override fun getCountries(): Flow<List<Country>> = flow {
        emit(fakeCountries)
    }

    override fun getCountry(code: String): Flow<Country> = flow {
        val country = fakeCountries.find { it.code == code } ?: fakeCountries.first()
        emit(country)
    }
}
