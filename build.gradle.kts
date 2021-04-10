plugins {
    id("jacoco")
}

buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven {
            setUrl("https://plugins.gradle.org/m2/0")
        }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${versions.project.agp}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${versions.lang.kotlin}")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }

    configurations.all {
        resolutionStrategy.force("org.objenesis:objenesis:2.6")
    }
}

tasks.create("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}

tasks.register("codeCoverageUnitTests", JacocoReport::class) {
    group = "code coverage"
    dependsOn(modules { it.tasks.findByName("testDebugUnitTest") })

    reports {
        csv.apply {
            isEnabled = false
        }
        xml.apply {
            isEnabled = false
        }
        html.apply {
            isEnabled = true
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
        csv.apply {
            isEnabled = false
        }
        xml.apply {
            isEnabled = false
        }
        html.apply {
            isEnabled = true
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