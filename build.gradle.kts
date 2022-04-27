// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        val navVersion = "2.4.2"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")
    }
}

plugins {
    id("com.android.application") version "7.3.0-alpha07" apply false
    id("com.android.library") version "7.3.0-alpha07" apply false
    kotlin("android") version "1.6.20" apply false
    kotlin("plugin.serialization") version "1.6.10"
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
