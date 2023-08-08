plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.13.3"
}

group = "twistdll.relative-lines"
version = "1.1.0"

repositories {
    mavenCentral()
}

intellij {
    version.set("2022.2")
    type.set("IC")

    plugins.set(listOf("youngstead.relative-line-numbers:1.1.1"))
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    runIde {
        autoReloadPlugins.set(true)
    }

    signPlugin {
        certificateChain.set(System.getenv("aboba"))
        privateKey.set(System.getenv("aboba"))
        password.set(System.getenv("aboba"))
    }

    publishPlugin {
        token.set(System.getenv("aboba"))
    }
}