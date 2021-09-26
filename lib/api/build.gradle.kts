plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("plugin.serialization") version versions.lang.kotlin
    id("jacoco")
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

    testOptions {
        unitTests {
            isReturnDefaultValues = false
            isIncludeAndroidResources = false
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

dependencies {
    implementation(project(":core:di"))
    implementation(project(":core:domain"))
    implementation(project(":core:exception"))
    implementation(project(":core:utils"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${versions.lang.kotlin}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${versions.lang.coroutines}")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.0")
    implementation("com.squareup.retrofit2:retrofit:${versions.network.retrofit}")
    implementation("com.squareup.okhttp3:logging-interceptor:${versions.network.okHttp3}")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${versions.network.retrofitKotlinSerialization}")
    implementation("org.koin:koin-core:${versions.di.koin}")

    testImplementation(project(":test:core-test"))
    testImplementation("org.jetbrains.kotlin:kotlin-reflect:${versions.lang.kotlin}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${versions.lang.coroutines}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${versions.testing.junitJupiter}")
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:${versions.testing.assertK}")
    testImplementation("com.squareup.okhttp3:mockwebserver:${versions.network.okHttp3}")
    testImplementation("org.koin:koin-test:${versions.di.koin}")
    testImplementation("org.mockito:mockito-core:${versions.testing.mockito}")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:${versions.testing.mockitoKotlin}") {
        exclude(group = "org.mockito", module = "mockito-core")
    }

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${versions.testing.junitJupiter}")

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:${versions.androidx.desugar}")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlinx.serialization.ExperimentalSerializationApi"
}
