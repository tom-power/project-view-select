plugins {
    id("java")
    kotlin("jvm") version "1.9.22"
    id("org.jetbrains.intellij") version "1.17.1"
}

group = "com.github.tompower.project-view-select"
version = "0.1.6"

repositories {
    mavenCentral()
}

intellij {
    version.set("2022.2.5")
    plugins.set(listOf("com.intellij.java"))
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks {
    // https://youtrack.jetbrains.com/issue/IDEA-278926/All-inheritors-of-UsefulTestCase-are-invisible-for-Gradle#focus=Comments-27-5561012.0-0
    val test by getting(Test::class) {
        setScanForTestClasses(false)
        // Only run tests from classes that end with "Test"
        include("**/*Test.class")
    }

    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    patchPluginXml {
        sinceBuild.set("222")
        untilBuild.set("")
    }

    // https://plugins.jetbrains.com/docs/intellij/publishing-plugin.html#deploying-a-plugin-with-gradle
    // https://plugins.jetbrains.com/docs/intellij/plugin-signing.html#signing-methods
    signPlugin {
        certificateChainFile.set(file("certificate/chain.crt"))
        privateKeyFile.set(file("certificate/private.pem"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    // https://plugins.jetbrains.com/author/me/tokens
    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
