import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

plugins {
    application
    kotlin("jvm") version "1.2.60"
}

version = "1.0-SNAPSHOT"

repositories {
    jcenter()
}

application {
    mainClassName = "infrastructure.console.MainKt"
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.github.ajalt:clikt:1.4.0")
    testImplementation("junit", "junit", "4.12")
}

java.sourceSets["main"].withConvention(KotlinSourceSet::class) {
    kotlin.srcDir(file("src/main/"))
}

java.sourceSets["test"].withConvention(KotlinSourceSet::class) {
    kotlin.srcDir(file("src/test/"))
}