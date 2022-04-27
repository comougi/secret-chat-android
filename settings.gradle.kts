pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "DaggerMultiModuleDemo"
include(":app")
include(":core:utils")
include(":core:common")
include(":core:network-api")
include(":core:network-impl")
include(":core:db-api")
include(":core:db-impl")
