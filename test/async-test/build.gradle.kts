plugins {
    id("java-library")
    id("kotlin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    compileOnly(project(":core:async"))
    compileOnly(libs.junit.jupiter)
    compileOnly(libs.kotlin.coroutines.test)
    compileOnly(libs.kotlin.stdlib)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
    kotlinOptions.languageVersion = "1.5"
}
