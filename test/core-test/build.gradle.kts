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
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${versions.lang.kotlin}")
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-test:${versions.lang.coroutines}")
    compileOnly("com.willowtreeapps.assertk:assertk-jvm:${versions.testing.assertK}")
    compileOnly("org.mockito:mockito-core:${versions.testing.mockito}")
    compileOnly("com.nhaarman.mockitokotlin2:mockito-kotlin:${versions.testing.mockitoKotlin}") {
        exclude(group = "org.mockito", module = "mockito-core")
    }
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:${versions.androidx.desugar}")
}
