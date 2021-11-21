plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
}

android {
    compileSdk = libs.versions.app.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.app.minSdk.get().toInt()
        targetSdk = libs.versions.app.targetSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    implementation(libs.androidx.lifecycle.livedata)
    implementation(libs.androidx.room)
    implementation(libs.androidx.paging)
    implementation(libs.koin.core)
    implementation(libs.kotlin.coroutines.android)
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.kotlin.stdlib.jdk7)

    kapt(libs.androidx.room.compiler)

    coreLibraryDesugaring(libs.androidx.desugar)

    testImplementation(project(":test:domain-test"))
    testImplementation(project(":test:core-test"))
    testImplementation(libs.assertk)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.koin.test)
    testImplementation(libs.kotlin.coroutines.test)
    testImplementation(libs.kotlin.reflect)
    testImplementation(libs.turbine)
    testImplementation(libs.bundles.mockito)

    testRuntimeOnly(libs.junit.jupiter.engine)

    androidTestImplementation(project(":test:domain-test"))
    androidTestImplementation(project(":test:core-test"))
    androidTestImplementation(libs.assertk)
    androidTestImplementation(libs.androidx.room.test)
    androidTestImplementation(libs.junit.legacy)
    androidTestImplementation(libs.kotlin.reflect)
    androidTestImplementation(libs.turbine)
    androidTestImplementation(libs.bundles.androidx.test)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlinx.serialization.ExperimentalSerializationApi"
}
