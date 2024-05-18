import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id ("com.github.johnrengelman.shadow")
}

dependencies {
    implementation ("com.google.guava:guava")
    implementation("org.slf4j:slf4j-api")
    implementation("ch.qos.logback:logback-classic")
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("gradleHelloOtus")
        archiveVersion.set("0.1")
        archiveClassifier.set("")
        manifest {
            attributes(mapOf("Main-Class" to "org.example.HelloOtus"))
        }
    }

    build {
        dependsOn(shadowJar)
    }
}