package com.example.explore.feature.list.mapper

import com.example.explore.core.model.Country
import com.example.explore.feature.list.CountryItemUi

fun Country.asUiModel() = CountryItemUi(
    code = code,
    name = name,
    capital = capital,
    region = region,
    flagUrl = flagUrl
)
