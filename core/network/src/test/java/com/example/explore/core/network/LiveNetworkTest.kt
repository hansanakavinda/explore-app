package com.example.explore.core.network

import com.example.explore.core.network.model.NetworkCountry
import com.example.explore.core.network.retrofit.CountryNetworkApi
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

class LiveNetworkTest {

    @Test
    fun testLiveApiKeyFetchOffsetPagination() = runBlocking {
        val apiKey = BuildConfig.REST_COUNTRIES_API_KEY
        if (apiKey == "rc_live_demo") {
            println("Skipping live test because key is demo key")
            return@runBlocking
        }

        val networkJson = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
            isLenient = true
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $apiKey")
                    .addHeader("User-Agent", "ExploreApp/1.0")
                    .build()
                chain.proceed(request)
            }
            .build()

        val api = Retrofit.Builder()
            .baseUrl("https://api.restcountries.com/")
            .client(okHttpClient)
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(CountryNetworkApi::class.java)

        val allCountries = mutableListOf<NetworkCountry>()
        var offset = 0
        var loopCount = 0
        while (true) {
            loopCount++
            val response = api.getAllCountries(limit = 100, offset = offset)
            val list = response.data?.objects ?: emptyList()
            if (list.isEmpty()) break
            allCountries.addAll(list)
            if (list.size < 100) break
            offset += list.size
        }

        println("Loop count: $loopCount, Total countries fetched: ${allCountries.size}")
        assertEquals(3, loopCount)
        assertTrue(allCountries.size > 200)
    }
}
