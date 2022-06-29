// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        val navVersion = "2.4.2"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")
    }
}

plugins {
    id("com.android.application") version "7.2.1" apply false
    id("com.android.library") version "7.2.1" apply false
    kotlin("android") version "1.6.21" apply false
    kotlin("plugin.serialization") version "1.6.10"
    id("com.savvasdalkitsis.module-dependency-graph") version "0.10"

}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
