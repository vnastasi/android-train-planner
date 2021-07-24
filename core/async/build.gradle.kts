plugins {
    id("java-library")
    id("kotlin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(project(":core:exception"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${versions.lang.kotlin}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${versions.lang.coroutines}")
}
