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
        useIR = true
    }
}

dependencies {
    compileOnly(project(":core:domain"))
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${versions.lang.kotlin}")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:${versions.androidx.desugar}")
}
