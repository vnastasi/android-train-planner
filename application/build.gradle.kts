import java.util.*

val appProperties: Properties =
    rootDir.resolve("application.properties")
        .takeIf { it.exists() && it.canRead() }
        ?.let { Properties().apply { load(it.inputStream()) } }
        ?: throw GradleException("Could not read 'application.properties'")

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    kotlin("android.extensions")
    id("org.jetbrains.kotlin.plugin.allopen") version versions.lang.kotlin
    id("jacoco")
}

android {
    compileSdkVersion(versions.project.targetSdk)

    defaultConfig {
        applicationId = "md.vnastasi.trainplanner"
        minSdkVersion(versions.project.minSdk)
        targetSdkVersion(versions.project.targetSdk)
        versionCode = 1
        versionName = "0.0.1"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            "String",
            "API_BASE_URL",
            "\"${appProperties.getProperty("application.api.base-url")}\""
        )
        buildConfigField(
            "String",
            "APP_USER",
            "\"${appProperties.getProperty("application.api.credentials.username")}\""
        )
        buildConfigField(
            "String",
            "APP_PASSWORD",
            "\"${appProperties.getProperty("application.api.credentials.password")}\""
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
        dataBinding = true
        viewBinding = true
        compose = false
        aidl = false
        renderScript = false
        resValues = true
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

androidExtensions {
    isExperimental = true
    features = setOf("parcelize")
}

allOpen {
    annotation("md.vnastasi.trainplanner.core.Open")
}

dependencies {

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
    implementation("androidx.lifecycle:lifecycle-livedata:${versions.androidx.lifecycle}")
    implementation("androidx.lifecycle:lifecycle-viewmodel:${versions.androidx.lifecycle}")
    implementation("androidx.lifecycle:lifecycle-extensions:${versions.androidx.lifecycle}")
    implementation("androidx.core:core-ktx:${versions.androidx.coreKtx}")
    implementation("androidx.fragment:fragment-ktx:${versions.androidx.fragment}")
    implementation("androidx.annotation:annotation:${versions.androidx.annotations}")
    implementation("org.koin:koin-core:${versions.di.koin}")
    implementation("org.koin:koin-android:${versions.di.koin}")
    implementation("com.github.razir.progressbutton:progressbutton:${versions.ui.progressButton}")
    implementation("com.github.skydoves:balloon:${versions.ui.baloon}")
    implementation("com.sagar:livedatapermission:${versions.ui.easyPermissions}")

    testImplementation("org.jetbrains.kotlin:kotlin-reflect:${versions.lang.kotlin}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${versions.lang.coroutines}")
    testImplementation("org.koin:koin-test:${versions.di.koin}")
    testImplementation("androidx.arch.core:core-testing:${versions.androidx.archCore}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${versions.testing.junitJupiter}")
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:${versions.testing.assertK}")
    testImplementation("org.mockito:mockito-core:${versions.testing.mockito}")
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
