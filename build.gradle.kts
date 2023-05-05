plugins {
    kotlin("jvm") version "1.8.20"
    id("org.jetbrains.kotlinx.kover") version "0.7.0-Beta"
}

group = "com.sportradar"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}


dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testImplementation("io.kotest:kotest-assertions-core-jvm:5.6.1")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}
