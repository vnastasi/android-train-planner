plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = libs.versions.app.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.app.minSdk.get().toInt()
        targetSdk = libs.versions.app.targetSdk.get().toInt()
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
    compileOnly(libs.bundles.mockito)

    coreLibraryDesugaring(libs.androidx.desugar)
}
