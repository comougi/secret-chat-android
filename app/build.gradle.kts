plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "com.ougi.secretchat"
        minSdk = 24
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    //core
    implementation(project(":core:utils"))
    implementation(project(":core:common"))
    implementation(project(":core:network:network-api"))
    implementation(project(":core:network:network-impl"))
//    implementation(project(":core:db:db-api"))
//    implementation(project(":core:db:db-impl"))
    implementation(project(":core:ui"))

    //features
    implementation(project(":features:workmanager-initializer"))
    implementation(project(":features:websocket:websocket-api"))
    implementation(project(":features:websocket:websocket-impl"))
    implementation(project(":features:encryption:encryption-api"))
    implementation(project(":features:encryption:encryption-impl"))
    implementation(project(":features:messaging:messaging-api"))
    implementation(project(":features:messaging:messaging-impl"))

    //screens
    implementation(project(":screens:password-screen:password-screen-api"))
    implementation(project(":screens:password-screen:password-screen-impl"))
    implementation(project(":screens:chat-list-screen:chat-list-screen-api"))
    implementation(project(":screens:chat-list-screen:chat-list-screen-impl"))

    //repositories
    implementation(project(":repositories:chat-repo:chat-repo-api"))
    implementation(project(":repositories:chat-repo:chat-repo-impl"))
    implementation(project(":repositories:server-info-repo:server-info-repo-api"))
    implementation(project(":repositories:server-info-repo:server-info-repo-impl"))
    implementation(project(":repositories:user-repo:user-repo-api"))
    implementation(project(":repositories:user-repo:user-repo-impl"))

    //dagger
    val daggerVersion = "2.41"
    implementation("com.google.dagger:dagger:$daggerVersion")
    kapt("com.google.dagger:dagger-compiler:$daggerVersion")

    //navigation
    val navVersion = "2.4.2"
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    //workmanager
    val workVersion = "2.7.1"
    implementation("androidx.work:work-runtime-ktx:$workVersion")

    //okhttp
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.3")

    //default
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.6.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}