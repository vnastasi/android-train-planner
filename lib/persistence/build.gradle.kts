plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
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

    sourceSets {
        sourceSets["test"].java.setSrcDirs(setOf(file("src/sharedTest/java"), file("src/test/java")))
        sourceSets["androidTest"].java.setSrcDirs(setOf(file("src/sharedTest/java"), file("src/androidTest/java")))
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

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${versions.lang.kotlin}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${versions.lang.coroutines}")
    implementation("org.koin:koin-core:${versions.di.koin}")
    implementation("androidx.room:room-runtime:${versions.androidx.room}")
    implementation("androidx.room:room-ktx:${versions.androidx.room}")

    kapt("androidx.room:room-compiler:${versions.androidx.room}")

    testImplementation("org.jetbrains.kotlin:kotlin-reflect:${versions.lang.kotlin}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${versions.lang.coroutines}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${versions.testing.junitJupiter}")
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:${versions.testing.assertK}")
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
