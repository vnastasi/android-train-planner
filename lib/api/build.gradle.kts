plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("jacoco")
    alias(libs.plugins.kotlin.serial)
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

    testOptions {
        unitTests {
            isReturnDefaultValues = false
            isIncludeAndroidResources = false
            all { options ->
                options.useJUnitPlatform {
                    includeEngines("junit-jupiter")
                }
                options.reports {
                    junitXml.required.set(false)
                    html.required.set(true)
                }
            }
        }
    }
}

dependencies {
    implementation(project(":core:di"))
    implementation(project(":core:domain"))
    implementation(project(":core:exception"))
    implementation(project(":core:utils"))
    implementation(libs.koin.core)
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.kotlin.serial.json)
    implementation(libs.kotlin.stdlib.jdk7)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlin.serial)

    coreLibraryDesugaring(libs.androidx.desugar)

    testImplementation(project(":test:core-test"))
    testImplementation(libs.assertk)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.koin.test)
    testImplementation(libs.kotlin.coroutines.test)
    testImplementation(libs.kotlin.reflect)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin) {
        exclude(group = "org.mockito", module = "mockito-core")
    }
    testImplementation(libs.okhttp.server)

    testRuntimeOnly(libs.junit.jupiter.engine)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlinx.serialization.ExperimentalSerializationApi"
}
