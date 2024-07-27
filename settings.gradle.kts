pluginManagement {
    val jgitver: String by settings
    val dependencyManagement: String by settings
    val springframeworkBoot: String by settings
    val johnrengelmanShadow: String by settings
    val jib: String by settings
    val protobufVer: String by settings
    val sonarlint: String by settings
    val spotless: String by settings
    val slf4j: String by settings

    plugins {
        id("fr.brouillard.oss.gradle.jgitver") version jgitver
        id("io.spring.dependency-management") version dependencyManagement
        id("org.springframework.boot") version springframeworkBoot
        id("com.github.johnrengelman.shadow") version johnrengelmanShadow
        id("com.google.cloud.tools.jib") version jib
        id("com.google.protobuf") version protobufVer
        id("name.remal.sonarlint") version sonarlint
        id("com.diffplug.spotless") version spotless
        id("org.slf4j") version slf4j
    }
}

rootProject.name = "java_2023_12"
include("hw01-gradle")
include("hw02-L04-generics")
include("hw03-L06-OwnTestFramework")
include("hw04-L08-GC")
include("hw05-L10-byteCode")
include("hw06-L12-ATM")
include("hw07-L15-Patterns")
include("hw08-L16-io")
include("hw09-L18-jdbc")
include("hw10-L21-jpql")
include("hw11-L22-cache")
include("hw12-L24-webServer")
