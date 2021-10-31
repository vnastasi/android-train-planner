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
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib:${versions.lang.kotlin}")
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:${versions.lang.coroutines}")
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-test:${versions.lang.coroutines}")
    compileOnly("org.junit.jupiter:junit-jupiter-api:${versions.testing.junitJupiter}")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
    kotlinOptions.languageVersion = "1.5"
}
