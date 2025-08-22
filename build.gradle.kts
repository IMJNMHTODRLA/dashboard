plugins {
    kotlin("jvm") version "2.0.0"
    id("com.gradleup.shadow") version "8.3.0"
    id("xyz.jpenilla.run-paper") version "2.3.1"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "_RedGold__"
version = "1.0.0"

val Name = "dashboard"
val Version = "1.0.0"

repositories {
    mavenCentral()
    maven {
        name = "papermc-repo"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    compileOnly("com.destroystokyo.paper:paper-api:1.13.2-R0.1-SNAPSHOT")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

tasks {
    runServer {
        // Configure the Minecraft version for our task.
        // This is the only required configuration besides applying the plugin.
        // Your plugin's jar (or shadowJar if present) will be used automatically.
        //minecraftVersion("1.13")
    }
}

val targetJavaVersion = 8
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

tasks.build {
    dependsOn(tasks.shadowJar)
    finalizedBy("copyPlugin")
    //finalizedBy("proguard")
    //finalizedBy("copyPlugin")
}

tasks.shadowJar {
    archiveFileName.set("$Name-$Version.jar") // 기본 jar 이름 그대로
    mergeServiceFiles()       // ACF 관련 서비스 파일 병합

    //re("co.aikar", "libs.acf-paper")
    //exclude("_RedGold__/**")
}

tasks.register<Copy>("copyPlugin") {
    doFirst { println("copying built plugin ...") }

    from("build/libs/$Name-$Version.jar")
    into("C:/Users/User/OneDrive/바탕 화면/plgins/마크서버테스트용플러그인/JavaGUIPlugins/plugins")

    doLast { println("copied built plugin!") }
    doNotTrackState("Plugin copy task does not produce incremental outputs")
}
