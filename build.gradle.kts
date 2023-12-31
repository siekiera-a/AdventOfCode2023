plugins {
    kotlin("jvm") version "1.9.20"
}

group = "pl.arkadiuszsiekiera"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}