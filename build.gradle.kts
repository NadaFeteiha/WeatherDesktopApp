import org.jetbrains.compose.compose
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
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")

}

dependencies {
    implementation(compose.desktop.currentOs)
    api(compose.foundation)
    api(compose.animation)
    val precompose_version= "1.4.3"
    api("moe.tlaster:precompose:$precompose_version")
    api("moe.tlaster:precompose-molecule:$precompose_version")
    api("moe.tlaster:precompose-viewmodel:$precompose_version")

    implementation("androidx.collection:collection:1.3.0-dev01")
    implementation("androidx.datastore:datastore-core-okio:1.1.0-dev01")
    implementation("androidx.datastore:datastore-preferences-core:1.1.0-dev01")

    implementation("io.github.xxfast:kstore:0.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2")
    implementation("io.github.xxfast:kstore-file:0.6.0")

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