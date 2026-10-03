package com.example.explore.core.database.di

import android.content.Context
import androidx.room.Room
import com.example.explore.core.database.ExploreDatabase
import com.example.explore.core.database.dao.CountryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesExploreDatabase(
        @ApplicationContext context: Context
    ): ExploreDatabase = Room.databaseBuilder(
        context,
        ExploreDatabase::class.java,
        "explore-database"
    ).build()

    @Provides
    fun providesCountryDao(
        database: ExploreDatabase
    ): CountryDao = database.countryDao()
}
