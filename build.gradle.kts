import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.20"
    id("org.jetbrains.compose") version "1.4.0"
    kotlin("plugin.serialization") version "1.8.20"
}

group = "me.nadafeteiha"
version = "1.0"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(compose.desktop.currentOs)

    val ktor_version = "2.3.1"
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("io.ktor:ktor-client-logging:$ktor_version")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    implementation ("org.slf4j:slf4j-simple:1.7.26")

    //coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")

    //koin
    implementation("io.insert-koin:koin-core:3.4.2")

}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
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