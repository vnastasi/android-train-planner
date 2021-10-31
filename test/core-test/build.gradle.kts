plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdk = versions.project.targetSdk

    defaultConfig {
        minSdk = versions.project.minSdk
        targetSdk = versions.project.targetSdk
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        dataBinding = false
        viewBinding = false
        compose = false
        aidl = false
        renderScript = false
        resValues = false
        shaders = false
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

dependencies {
    compileOnly(libs.assertk)
    compileOnly(libs.kotlin.coroutines.test)
    compileOnly(libs.kotlin.stdlib.jdk7)
    compileOnly(libs.mockito.core)
    compileOnly(libs.mockito.kotlin) {
        exclude(group = "org.mockito", module = "mockito-core")
    }

    coreLibraryDesugaring(libs.androidx.desugar)
}
