plugins {
    id("jacoco")
}

buildscript {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        maven {
            setUrl("https://plugins.gradle.org/m2/0")
        }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${libs.versions.kotlin.core.get()}")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${libs.versions.androidx.navigation.get()}")
    }
}

allprojects {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
    }
}

tasks.create("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}

tasks.register("codeCoverageUnitTests", JacocoReport::class) {
    group = "code coverage"
    dependsOn(modules { it.tasks.findByName("testDebugUnitTest") })

    reports {
        csv.required.set(false)
        xml.required.set(false)
        html.apply {
            required.set(true)
            outputLocation.set(buildDir.resolve("reports/codeCoverage/unitTests"))
        }
    }

    sourceDirectories.setFrom(modules { it.projectDir.resolve("src/main/java") })
    classDirectories.setFrom(modules { it.buildDir.resolve("tmp/kotlin-classes/debug") }.asFileTree.matching {
        excludes += setOf(
                "**/domain/**" // Do not include domain data classes
        )
    })
    executionData.setFrom(modules { it.buildDir.resolve("jacoco") }.asFileTree.matching {
        includes += "*.exec"
    })
}

tasks.register("codeCoverageAll", JacocoReport::class) {
    group = "code coverage"
    dependsOn(
        modules { it.tasks.findByName("testDebugUnitTest") },
        modules { it.tasks.findByName("createDebugCoverageReport") }
    )

    reports {
        csv.required.set(false)
        xml.required.set(false)
        html.apply {
            required.set(true)
            outputLocation.set(buildDir.resolve("reports/codeCoverage/all"))
        }
    }

    sourceDirectories.setFrom(modules { it.projectDir.resolve("src/main/java") })
    classDirectories.setFrom(modules { it.buildDir.resolve("tmp/kotlin-classes/debug") })
    executionData.setFrom(
        modules { it.buildDir.resolve("jacoco") }.asFileTree.matching { includes += "*.exec" },
        modules { it.buildDir.resolve("outputs/code_coverage") }.asFileTree.matching { includes += "**/*.ec" }
    )
}

fun <T> modules(block: (Project) -> T): ConfigurableFileCollection = files(subprojects.mapNotNull(block))