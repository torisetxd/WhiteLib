plugins {
    id("java")
    id("java-library")
    id("maven-publish")
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