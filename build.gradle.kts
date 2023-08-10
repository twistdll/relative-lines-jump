plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.15.0"
}

group = "twistdll.relative-lines"
version = "1.1.0"

repositories {
    mavenCentral()
}

intellij {
    version.set("2023.2")
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

    patchPluginXml {
        sinceBuild.set("222")
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