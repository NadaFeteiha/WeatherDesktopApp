@file:Suppress("DSL_SCOPE_VIOLATION")
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.FileInputStream
import java.util.*


plugins {
    kotlin("jvm") version "1.9.0"
    kotlin("plugin.serialization") version "1.8.20"
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kotlinKsp)
    id("com.github.gmazzo.buildconfig") version "4.1.2"
}

group = "me.nadafeteiha"
version = "1.0"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

val localProperties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        load(FileInputStream(localPropertiesFile))
    }
}

buildConfig {
    buildConfigField("String", "WEATHER_API_KEY", "\"${localProperties.getProperty("WEATHER_API_KEY")}\"")
    buildConfigField("String", "LOCATION_API_KEY", "\"${localProperties.getProperty("LOCATION_API_KEY")}\"")
}

dependencies {
    implementation(compose.desktop.currentOs)

//    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("org.slf4j:slf4j-simple:1.7.26")

    api(libs.ktor.client.core)
    api(libs.ktor.client.cio)
    api(libs.ktor.json.serialization)
    api(libs.kotlin.serialization)
    api(libs.ktor.content.negotiation)
    api(libs.ktor.logging)
    api(libs.ktor.gson)

    implementation(libs.voyager.navigator)
    implementation(libs.voyager.transitions)

    //coroutines
    implementation(libs.kotlin.coroutines)

    //koin
    api(libs.koin.core)
    implementation(libs.koin.annotations)
    implementation(libs.koin.ksp)

    //image
    api(libs.compose.image.loader)

}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "WeatherApp"
            packageVersion = "1.0.0"
        }
    }
}
val compileKotlin: KotlinCompile by tasks

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}