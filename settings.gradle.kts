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
include(":core:ui")
include(":core:network:network-api")
include(":core:network:network-impl")
include(":core:db:db-api")
include(":core:db:db-impl")
include(":features:workmanager-initializer")
include(":features:websocket:websocket-api")
include(":features:websocket:websocket-impl")
include(":features:encryption:encryption-api")
include(":features:encryption:encryption-impl")
include(":screens:password-screen:password-screen-api")
include(":screens:password-screen:password-screen-impl")
include(":screens:chat-list-screen:chat-list-screen-api")
include(":screens:chat-list-screen:chat-list-screen-impl")
include(":repositories:chat-repo:chat-repo-api")
include(":repositories:chat-repo:chat-repo-impl")
include(":repositories:server-info-repo:server-info-repo-api")
include(":repositories:server-info-repo:server-info-repo-impl")
include(":repositories:user-repo:user-repo-api")
include(":repositories:user-repo:user-repo-impl")
include(":features:messaging:messaging-api")
include(":features:messaging:messaging-impl")
include(":screens:chat-screen:chat-screen-api")
include(":screens:chat-screen:chat-screen-impl")
include(":repositories:message-repo:message-repo-api")
include(":repositories:message-repo:message-repo-impl")
