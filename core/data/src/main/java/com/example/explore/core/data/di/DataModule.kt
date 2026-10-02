package com.example.explore.core.data.di

import com.example.explore.core.data.repository.CountryRepository
import com.example.explore.core.data.repository.fake.FakeCountryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsCountryRepository(
        fakeCountryRepository: FakeCountryRepository
    ): CountryRepository
}
