package com.example.explore.feature.detail.mapper

import com.example.explore.core.model.Country
import com.example.explore.feature.detail.CountryDetailUi

fun Country.asUiModel() = CountryDetailUi(
    code = code,
    name = name,
    capital = capital,
    region = region,
    subregion = subregion,
    population = population,
    area = area,
    flagUrl = flagUrl,
    currencies = currencies,
    languages = languages,
    timezones = timezones
)
