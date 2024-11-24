import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    id("com.github.johnrengelman.shadow") version "8.1.1"  // Add Shadow plugin
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21)) // Java 21 for compatibility
    }
}

kotlin {
    jvmToolchain(21) // Kotlin matches Java version
}

dependencies {
    implementation(compose.desktop.currentOs)
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Game"
            packageVersion = "1.0.0"
        }
    }
}

// Configure the 'shadowJar' task to include all dependencies in the JAR
tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "MainKt"
    }
}

// Optional: Configure the regular 'jar' task (for debugging or non-shadow cases)
tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "MainKt"
    }
}
