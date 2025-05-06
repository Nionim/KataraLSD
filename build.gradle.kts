plugins {
    kotlin("jvm") version "2.1.0"
    id("com.gradleup.shadow") version "8.3.0"
    id("de.eldoria.plugin-yml.bukkit") version "0.7.1"
}

// ===================== //
//        Depends        //
// ===================== //

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

// ===================== //
//        Process        //
// ===================== //

group = "delta.cion"
version = "1.0.0-Release"

bukkit {
    name = rootProject.name
    version = this.version
    main = "delta.cion.KataraLSD"

    authors = listOf("Carde2")
    description = "By: DeltaCion"
    website = "https://discord.gg/MEBkvJbe4P"
    apiVersion = "1.19"
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    shadowJar {
        mergeServiceFiles()
        archiveClassifier.set("")
    }
}