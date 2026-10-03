plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}

android {
    namespace = "com.example.explore.core.database"
    compileSdk = 37

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

ksp {
    arg("room.generateKotlin", "true")
}

dependencies {
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.room.paging)
    ksp(libs.room.compiler)
}
