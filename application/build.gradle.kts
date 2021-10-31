import java.util.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val appProperties: Properties =
    rootDir.resolve("application.properties")
        .takeIf { it.exists() && it.canRead() }
        ?.let { Properties().apply { load(it.inputStream()) } }
        ?: throw GradleException("Could not read 'application.properties'")

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-android")
    id("jacoco")
    alias(libs.plugins.kotlin.allopen)
}

android {
    compileSdk = libs.versions.app.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "md.vnastasi.trainplanner"
        minSdk = libs.versions.app.minSdk.get().toInt()
        targetSdk = libs.versions.app.targetSdk.get().toInt()
        versionCode = 1
        versionName = "0.0.1"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            type ="String",
            name = "API_BASE_URL",
            value = "\"${appProperties.getProperty("application.api.base-url")}\""
        )
        buildConfigField(
            type ="String",
            name = "APP_USER",
            value = "\"${appProperties.getProperty("application.api.credentials.username")}\""
        )
        buildConfigField(
            type = "String",
            name = "APP_PASSWORD",
            value = "\"${appProperties.getProperty("application.api.credentials.password")}\""
        )
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName("debug") {
            isTestCoverageEnabled = true
        }
    }

    buildFeatures {
        dataBinding = false
        viewBinding = true
        compose = false
        aidl = false
        renderScript = false
        resValues = true
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

    sourceSets {
        sourceSets["test"].java.setSrcDirs(
            setOf(
                file("src/sharedTest/java"),
                file("src/test/java")
            )
        )
        sourceSets["androidTest"].java.setSrcDirs(
            setOf(
                file("src/sharedTest/java"),
                file("src/androidTest/java")
            )
        )
    }

    testOptions {
        unitTests {
            isReturnDefaultValues = true
            isIncludeAndroidResources = true
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

allOpen {
    annotation("md.vnastasi.trainplanner.core.Open")
}

dependencies {

    implementation(project(":core:async"))
    implementation(project(":core:di"))
    implementation(project(":core:exception"))
    implementation(project(":core:ui"))
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.arch.runtime)
    implementation(libs.androidx.core)
    implementation(libs.androidx.datastore)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.preference)
    implementation(libs.google.play.location)
    implementation(libs.google.play.maps)
    implementation(libs.koin.android)
    implementation(libs.koin.core)
    implementation(libs.kotlin.coroutines.android)
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.kotlin.stdlib.jdk7)
    implementation(libs.bundles.androidx.lifecycle)
    implementation(libs.bundles.androidx.navigation)
    implementation(libs.bundles.androidx.ui)

    debugImplementation(libs.androidx.fragment.test)

    coreLibraryDesugaring(libs.androidx.desugar)

    testImplementation(project(":test:core-test"))
    testImplementation(project(":test:async-test"))
    testImplementation(libs.androidx.arch.test)
    testImplementation(libs.assertk)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.koin.test)
    testImplementation(libs.kotlin.coroutines.test)
    testImplementation(libs.kotlin.reflect)
    testImplementation(libs.bundles.mockito)

    testRuntimeOnly(libs.junit.jupiter.engine)

    androidTestImplementation(libs.androidx.arch.test)
    androidTestImplementation(libs.androidx.navigation.test)
    androidTestImplementation(libs.koin.test) {
        exclude(group = "org.mockito", module = "mockito-inline")
    }
    androidTestImplementation(libs.mockito.android)
    androidTestImplementation(libs.bundles.androidx.espresso)
    androidTestImplementation(libs.bundles.androidx.test)
    androidTestImplementation(libs.bundles.mockito)
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
    kotlinOptions.languageVersion = "1.5"
}
