import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    id("org.jetbrains.compose") version "1.1.1"
   kotlin("plugin.serialization") version "1.6.10"
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
    val ktor_version = "2.0.0"
//    implementation("io.ktor:ktor-client-okhttp:$ktor_version")
//    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
//    implementation("io.ktor:ktor-client-serialization:$ktor_version")
//    implementation("io.ktor:ktor-serialization-gson:$ktor_version")
//    implementation("io.ktor:ktor-client-logging-jvm:1.5.0")
//    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")

    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("io.ktor:ktor-client-logging:$ktor_version")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
//    //coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")

//
//    sourceSets {
//        val ktorVersion = "2.3.2"
//        val commonMain by getting {
//            dependencies {
//                implementation("io.ktor:ktor-client-core:$ktorVersion")
//            }
//        }
//    }
    implementation(kotlin("stdlib-jdk8"))

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
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}