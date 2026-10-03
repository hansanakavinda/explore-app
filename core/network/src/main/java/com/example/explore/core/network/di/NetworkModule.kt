package com.example.explore.core.network.di

import com.example.explore.core.network.BuildConfig
import com.example.explore.core.network.retrofit.CountryNetworkApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.Call
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
    }

    @Provides
    @Singleton
    fun okHttpCallFactory(): Call.Factory {
        val apiKey = BuildConfig.REST_COUNTRIES_API_KEY
        val authInterceptor = Interceptor { chain ->
            val authHeaderValue = when {
                apiKey.startsWith("Bearer ", ignoreCase = true) -> apiKey
                apiKey == "rc_live_demo" -> "rc_live_demo"
                else -> "Bearer $apiKey"
            }

            val request = chain.request().newBuilder()
                .addHeader("Authorization", authHeaderValue)
                .addHeader("User-Agent", "ExploreApp/1.0")
                .build()
            chain.proceed(request)
        }

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }
            )
            .build()
    }

    @Provides
    @Singleton
    fun providesCountryNetworkApi(
        networkJson: Json,
        okhttpCallFactory: Call.Factory,
    ): CountryNetworkApi {
        return Retrofit.Builder()
            .baseUrl("https://api.restcountries.com/")
            .callFactory(okhttpCallFactory)
            .addConverterFactory(
                networkJson.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(CountryNetworkApi::class.java)
    }
}
