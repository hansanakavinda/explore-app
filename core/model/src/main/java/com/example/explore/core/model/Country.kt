package com.example.explore.core.model

data class Country(
    val code: String,
    val name: String,
    val capital: String,
    val region: String,
    val subregion: String,
    val population: String,
    val area: String,
    val flagUrl: String,
    val currencies: List<String>,
    val languages: List<String>,
    val timezones: List<String>
)
