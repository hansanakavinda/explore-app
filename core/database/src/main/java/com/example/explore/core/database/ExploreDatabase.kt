package com.example.explore.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.explore.core.database.dao.CountryDao
import com.example.explore.core.database.model.CountryEntity

@Database(
    entities = [CountryEntity::class],
    version = 1,
    exportSchema = true
)
abstract class ExploreDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao
}
