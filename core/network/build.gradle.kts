import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.explore.core.network"
    compileSdk = 37

    defaultConfig {
        minSdk = 24

        val properties = Properties()
        val localPropertiesFile = rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            properties.load(localPropertiesFile.inputStream())
        }
        val apiKey = properties.getProperty("REST_COUNTRIES_API_KEY") ?: "rc_live_demo"
        buildConfigField("String", "REST_COUNTRIES_API_KEY", "\"$apiKey\"")
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    
    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlinx.serialization)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging)
    
    testImplementation(libs.junit)
    testImplementation(libs.mockwebserver)
    testImplementation(libs.coroutines.test)
}
