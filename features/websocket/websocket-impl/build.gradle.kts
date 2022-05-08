plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.serialization") version "1.6.10"
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 24
        targetSdk = 31

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    //import
    implementation(project(":core:common"))
    implementation(project(":core:utils"))
    implementation(project(":features:websocket:websocket-api"))

    //dagger
    val daggerVersion = "2.41"
    implementation("com.google.dagger:dagger:$daggerVersion")
    kapt("com.google.dagger:dagger-compiler:$daggerVersion")

    //workmanager
    val workVersion = "2.7.1"
    implementation("androidx.work:work-runtime-ktx:$workVersion")

    //okhttp
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.3")

    //coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")

    //serialization
    val serializationVersion = "1.3.2"
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")

    //default
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.6.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}