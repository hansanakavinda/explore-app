package com.example.explore.core.data.model

import com.example.explore.core.database.model.CountryEntity
import com.example.explore.core.model.Country
import com.example.explore.core.network.model.NetworkCountry

fun NetworkCountry.asEntity() = CountryEntity(
    code = codes?.alpha_3 ?: "Unknown",
    name = names?.common ?: "Unknown",
    capital = capitals?.firstOrNull()?.name ?: "N/A",
    region = region ?: "N/A",
    subregion = subregion ?: "N/A",
    population = population ?: 0L,
    area = area?.kilometers ?: 0.0,
    flagUrl = flag?.url_png ?: "",
    currencies = currencies?.mapNotNull { it.name }?.joinToString(", ") ?: "N/A",
    languages = languages?.mapNotNull { it.name }?.joinToString(", ") ?: "N/A",
    timezones = timezones?.joinToString(", ") ?: "N/A"
)

fun CountryEntity.asExternalModel() = Country(
    code = code,
    name = name,
    capital = capital,
    region = region,
    subregion = subregion,
    population = formatPopulation(population),
    area = "$area km²",
    flagUrl = flagUrl,
    currencies = currencies.split(", ").filter { it.isNotBlank() },
    languages = languages.split(", ").filter { it.isNotBlank() },
    timezones = timezones.split(", ").filter { it.isNotBlank() }
)

private fun formatPopulation(population: Long): String {
    return when {
        population >= 1_000_000_000 -> String.format("%.1f billion", population / 1_000_000_000.0)
        population >= 1_000_000 -> String.format("%.1f million", population / 1_000_000.0)
        else -> "%,d".format(population)
    }
}
