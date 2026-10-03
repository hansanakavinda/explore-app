pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Explore"
include(":app")
include(":core:ui")
include(":core:network")
include(":core:database")
include(":core:model")
include(":core:data")
include(":core:domain")
include(":feature:list")
include(":feature:detail")
