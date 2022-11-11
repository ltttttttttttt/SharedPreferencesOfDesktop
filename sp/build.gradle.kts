plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

group = "com.lt"
version = "1.0"

repositories {
    maven("https://jitpack.io")
    mavenCentral()
}

dependencies {
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    implementation("com.github.ltttttttttttt:DataStructure:1.0.5")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}