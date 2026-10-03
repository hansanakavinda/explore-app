package com.example.explore.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class CountryEntity(
    @PrimaryKey
    val code: String,
    val name: String,
    val capital: String,
    val region: String,
    val subregion: String,
    val population: Long,
    val area: Double,
    val flagUrl: String,
    val currencies: String, // Flattened: "LKR, USD"
    val languages: String,  // Flattened: "Sinhala, English"
    val timezones: String   // Flattened: "UTC+5:30"
)
