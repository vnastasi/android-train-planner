import java.util.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val appProperties: Properties =
    rootDir.resolve("application.properties")
        .takeIf { it.exists() && it.canRead() }
        ?.let { Properties().apply { load(it.inputStream()) } }
        ?: throw GradleException("Could not read 'application.properties'")

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("org.jetbrains.kotlin.plugin.allopen") version versions.lang.kotlin
    id("jacoco")
    id("kotlin-android")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdk = versions.project.targetSdk

    defaultConfig {
        applicationId = "md.vnastasi.trainplanner"
        minSdk = versions.project.minSdk
        targetSdk = versions.project.targetSdk
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
        useIR = true
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
                    junitXml.isEnabled = false
                    html.isEnabled = true
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

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${versions.lang.kotlin}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${versions.lang.coroutines}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${versions.lang.coroutines}")
    implementation("androidx.appcompat:appcompat:${versions.androidx.appCompat}")
    implementation("androidx.constraintlayout:constraintlayout:${versions.androidx.constraintLayout}")
    implementation("androidx.recyclerview:recyclerview:${versions.androidx.recyclerview}")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:${versions.androidx.swipeRefreshLayout}")
    implementation("com.google.android.material:material:${versions.androidx.materialDesign}")
    implementation("com.google.android.gms:play-services-location:${versions.androidx.playServices}")
    implementation("androidx.arch.core:core-runtime:${versions.androidx.archCore}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${versions.androidx.lifecycle}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${versions.androidx.lifecycle}")
    implementation("androidx.core:core-ktx:${versions.androidx.coreKtx}")
    implementation("androidx.fragment:fragment-ktx:${versions.androidx.fragment}")
    implementation("androidx.annotation:annotation:${versions.androidx.annotations}")
    implementation("androidx.navigation:navigation-fragment-ktx:${versions.androidx.navigation}")
    implementation("androidx.navigation:navigation-ui-ktx:${versions.androidx.navigation}")
    implementation("androidx.datastore:datastore-preferences:${versions.androidx.dataStore}")
    implementation("androidx.preference:preference-ktx:${versions.androidx.preference}")
    implementation("org.koin:koin-core:${versions.di.koin}")
    implementation("org.koin:koin-android:${versions.di.koin}")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("com.google.android.gms:play-services-maps:17.0.1")

    testImplementation(project(":test:core-test"))
    testImplementation(project(":test:async-test"))

    testImplementation("org.jetbrains.kotlin:kotlin-reflect:${versions.lang.kotlin}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${versions.lang.coroutines}")
    testImplementation("org.koin:koin-test:${versions.di.koin}")
    testImplementation("androidx.arch.core:core-testing:${versions.androidx.archCore}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${versions.testing.junitJupiter}")
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:${versions.testing.assertK}")
    testImplementation("org.mockito:mockito-core:${versions.testing.mockito}")
    testImplementation("org.mockito:mockito-inline:${versions.testing.mockito}")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:${versions.testing.mockitoKotlin}") {
        exclude(group = "org.mockito", module = "mockito-core")
    }

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${versions.testing.junitJupiter}")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:${versions.testing.junitJupiter}")

    androidTestImplementation("androidx.test:core:${versions.androidx.test}")
    androidTestImplementation("androidx.test:runner:${versions.androidx.test}")
    androidTestImplementation("androidx.test:rules:${versions.androidx.test}")
    androidTestImplementation("androidx.test.ext:junit:${versions.androidx.testJunit}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${versions.androidx.espresso}")
    androidTestImplementation("androidx.test.espresso:espresso-intents:${versions.androidx.espresso}")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:${versions.androidx.espresso}")
    androidTestImplementation("androidx.arch.core:core-testing:${versions.androidx.archCore}")
    androidTestImplementation("androidx.navigation:navigation-testing:${versions.androidx.navigation}")
    androidTestImplementation("org.koin:koin-test:${versions.di.koin}") {
        exclude(group = "org.mockito", module = "mockito-inline")
    }
    androidTestImplementation("org.mockito:mockito-android:${versions.testing.mockito}")
    androidTestImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:${versions.testing.mockitoKotlin}") {
        exclude(group = "org.mockito", module = "mockito-core")
    }

    debugImplementation("androidx.fragment:fragment-testing:${versions.androidx.fragment}")

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:${versions.androidx.desugar}")
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
    kotlinOptions.languageVersion = "1.4"
}
