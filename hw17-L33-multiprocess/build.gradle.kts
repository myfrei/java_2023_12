import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.ofSourceSet
import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.plugins
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc

plugins {
    id("java")
    id("idea")
    id("com.google.protobuf") version "0.8.18"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.grpc:grpc-netty:1.42.1")
    implementation("io.grpc:grpc-protobuf:1.42.1")
    implementation("io.grpc:grpc-stub:1.42.1")
    implementation("com.google.protobuf:protobuf-java:3.19.1")
    compileOnly("javax.annotation:javax.annotation-api:1.3.2")
    implementation("com.google.errorprone:error_prone_annotations:2.10.0")
    implementation("org.apache.tomcat:annotations-api:6.0.53")
    implementation("org.slf4j:slf4j-api:1.7.25")
    implementation("ch.qos.logback:logback-classic:1.2.6")
}

val protoSrcDir = "$projectDir/build/generated/source/proto"

idea {
    module {
        sourceDirs = sourceDirs.plus(file(protoSrcDir))
        generatedSourceDirs = generatedSourceDirs.plus(file(protoSrcDir))
    }
}

sourceSets {
    main {
        java.srcDir(protoSrcDir)
    }
}

protobuf {
    generatedFilesBaseDir = "$projectDir/build/generated"

    protoc {
        artifact = "com.google.protobuf:protoc:3.19.1"
    }

    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.42.1"
        }
    }

    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                id("grpc")
            }
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

afterEvaluate {
    tasks {
        getByName("generateProto").dependsOn(processResources)
    }
}