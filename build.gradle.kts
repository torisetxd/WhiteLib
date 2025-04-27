plugins {
    id("java")
    id("java-library")
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "mc.toriset"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        name = "papermc-repo"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
}

dependencies {
//    testImplementation(platform("org.junit:junit-bom:5.10.0"))
//    testImplementation("org.junit.jupiter:junit-jupiter")
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
    implementation("org.mongodb:mongodb-driver-reactivestreams:4.11.0")
    implementation("io.projectreactor:reactor-core:3.5.10")
}

tasks.shadowJar {
    relocate("com.mongodb", "mc.toriset.lib.mongodb")
    relocate("org.bson", "mc.toriset.lib.bson")
    relocate("org.reactivestreams", "mc.toriset.lib.reactivestreams")
    relocate("reactor", "mc.toriset.lib.reactor")
    relocate("io.projectreactor", "mc.toriset.lib.projectreactor")

    archiveClassifier.set("")
}


//tasks.test {
//    useJUnitPlatform()
//}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "com.github.torisetxd"
            artifactId = "WhiteLib"
            version = project.version.toString()

            from(components["java"])
        }
    }

    repositories {
        maven {
            name = "jitpack"
            url = uri("https://jitpack.io")
        }
    }
}