plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    kotlin("plugin.serialization") version "2.0.0"

    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("app.cash.sqldelight") version "2.0.2"
}

android {
    namespace = "com.pxl.pixelstore"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.pxl.pixelstore"
        minSdk = 29
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.compose.navigation)
    implementation(libs.androidx.material.icons)
    implementation(libs.androidx.material.icons.extended)

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.10.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")

    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
//    implementation("androidx.recyclerview:recyclerview:1.3.2")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.9.2")

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.2")

//    implementation("io.ktor:ktor-client-okhttp:2.3.0")

    implementation("androidx.work:work-runtime-ktx:2.10.2")

    implementation(libs.dagger.hilt)
//    implementation(libs.hilt.navigation)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.android.compiler)

//    implementation("io.ktor:ktor-client-core:2.3.0")
//    implementation("io.ktor:ktor-client-content-negotiation:2.3.0")
//    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.0")
//    implementation("io.ktor:ktor-client-okhttp:2.3.0")
//    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.0")

    implementation("app.cash.sqldelight:runtime:2.0.2")
    implementation("app.cash.sqldelight:coroutines-extensions:2.0.2")
    implementation("app.cash.sqldelight:android-driver:2.0.2")

    implementation("androidx.datastore:datastore-preferences:1.1.7")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}