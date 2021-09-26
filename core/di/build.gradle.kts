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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
        useIR = true
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${versions.lang.kotlin}")
    implementation("io.insert-koin:koin-core:${versions.di.koin}")
    implementation("io.insert-koin:koin-android:${versions.di.koin}")
}
