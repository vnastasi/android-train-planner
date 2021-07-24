plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdkVersion(versions.project.targetSdk)

    defaultConfig {
        minSdkVersion(versions.project.minSdk)
        targetSdkVersion(versions.project.targetSdk)
        versionCode = versions.project.versionCode
        versionName = versions.project.versionName
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        useIR = true
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${versions.lang.kotlin}")
    implementation("androidx.lifecycle:lifecycle-livedata:${versions.androidx.lifecycle}")
    implementation("androidx.lifecycle:lifecycle-viewmodel:${versions.androidx.lifecycle}")
    implementation("androidx.lifecycle:lifecycle-extensions:${versions.androidx.lifecycle}")
    implementation("androidx.core:core-ktx:${versions.androidx.coreKtx}")
    implementation("androidx.fragment:fragment-ktx:${versions.androidx.fragment}")
    implementation("androidx.annotation:annotation:${versions.androidx.annotations}")
}
