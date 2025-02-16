plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.shadow)
}

group = "net.azisaba"
version = "0.1.0-beta"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
    maven("https://oss.sonatype.org/content/groups/public/") {
        name = "sonatype"
    }
    maven("https://repo.aikar.co/content/groups/aikar/") {
        name = "aikar-repo"
    }
    maven("https://repo.azisaba.net/repository/maven-releases/") {
        name = "azisaba-repo"
    }
    maven("https://mvn.lumine.io/repository/maven-public/") {
        name = "lumine-repo"
    }
}

dependencies {
    // system
    compileOnly(libs.paper.api)
    implementation(libs.kotlin.stdlib.jdk8)

    // library
    implementation(libs.acf.paper)
    implementation(libs.kaml)

    // plugin
    compileOnly(libs.rcitemlogging)
    compileOnly(libs.mythic.dist)
}

val targetJavaVersion = 21
kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}

// === For ACF-Paper===
tasks.compileJava {
    options.compilerArgs.add("-parameters")
}

tasks.compileKotlin {
    compilerOptions.javaParameters = true
}

tasks.shadowJar {
    relocate("co.aikar.commands", "net.azisaba.rcgacha.shadow.acf")
    relocate("co.aikar.locales", "net.azisaba.rcgacha.shadow.locales")
}
// ====================
