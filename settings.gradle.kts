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
include(":core:network:network-api")
include(":core:network:network-impl")
//include(":core:db:db-api")
//include(":core:db:db-impl")
include(":features:workmanager-initializer")
include(":features:websocket:websocket-api")
include(":features:websocket:websocket-impl")
include(":features:encryption:encryption-api")
include(":features:encryption:encryption-impl")
include(":features:datastore:datastore-api")
include(":features:datastore:datastore-impl")
