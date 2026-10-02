package com.example.explore.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.explore.core.database.model.CountryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDao {
    @Query("""
        SELECT * FROM countries 
        WHERE name LIKE '%' || :query || '%' 
        ORDER BY name ASC
    """)
    fun getCountries(query: String): PagingSource<Int, CountryEntity>

    @Query("SELECT * FROM countries WHERE code = :code")
    fun getCountry(code: String): Flow<CountryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(countries: List<CountryEntity>): List<Long>

    @Query("DELETE FROM countries")
    suspend fun clearAll(): Int
}
