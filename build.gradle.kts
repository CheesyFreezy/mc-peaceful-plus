plugins {
    id("java")
    id("com.gradleup.shadow") version "8.3.5"
    id("xyz.jpenilla.run-paper") version "3.0.2"
}

group = "com.orangecheese"
version = "0.1-ALPHA"

repositories {
    mavenCentral()

    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }

    maven {
        url = uri("https://jitpack.io")
    }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    compileOnly("io.papermc.paper:paper-api:26.2.build.+")

    implementation("com.github.slighterr12:SimpleJSONConfig:1.4.3")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}

tasks.withType(xyz.jpenilla.runtask.task.AbstractRun::class) {
    javaLauncher = javaToolchains.launcherFor {
        vendor = JvmVendorSpec.JETBRAINS
        languageVersion = JavaLanguageVersion.of(25)
    }
    jvmArgs(
        "-XX:+AllowEnhancedClassRedefinition",
        "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"
    )
}

tasks {
    shadowJar {
        archiveClassifier.set("")
    }

    test {
        useJUnitPlatform()
    }

    runServer {
        minecraftVersion("26.2")
        downloadPlugins {

        }
    }
}