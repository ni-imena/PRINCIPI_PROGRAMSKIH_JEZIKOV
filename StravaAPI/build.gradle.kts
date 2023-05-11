import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven("https://repo1.maven.org/maven2/")
}

kotlin {
    jvm {
        jvmToolchain(11)
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
                implementation("org.json:json:20220320")
                implementation("org.slf4j:slf4j-api:1.7.25")
                implementation("org.openjfx:javafx-web:21-ea+5")
                implementation("io.ktor:ktor-server-core-jvm:2.3.0")
                implementation("io.ktor:ktor-server-netty-jvm:2.3.0")
                implementation("io.ktor:ktor-server-status-pages-jvm:2.3.0")
                implementation("io.ktor:ktor-server-default-headers-jvm:2.3.0")
                implementation("com.squareup.okhttp3:okhttp:4.10.0")

                implementation("io.netty:netty-all:4.1.70.Final")
                implementation("org.slf4j:slf4j-api:1.7.32")
                implementation("ch.qos.logback:logback-classic:1.2.6")

            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "StravaAPI"
            packageVersion = "1.0.0"
        }
    }
}
