plugins {
    kotlin("jvm") version "2.1.0"
    id("com.gradleup.shadow") version "8.3.0"
}

group = "delta.cion"
version = "1.0.0-Release"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
    maven("https://oss.sonatype.org/content/groups/public/") {
        name = "sonatype"
    }
}

dependencies {
    implementation("org.telegram:telegrambots:6.7.0")
    implementation("org.json:json:20240303")
    compileOnly("io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")
}

kotlin {
    jvmToolchain(17)
}

tasks {
    build {
        dependsOn(shadowJar)
        dependsOn(processResources)
    }

    shadowJar {
        mergeServiceFiles()
        archiveClassifier.set("")
    }

    processResources {
        val props = mapOf("version" to version)
        inputs.properties(props)
        filteringCharset = "UTF-8"
        filesMatching("plugin.yml") {
            expand(props)
        }
    }
}