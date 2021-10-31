plugins {
    id("java-library")
    id("kotlin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation(project(":core:exception"))
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.kotlin.stdlib)
}
