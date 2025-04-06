pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        // Добавляем JitPack для библиотек из GitHub
        maven { setUrl("https://jitpack.io") }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // Добавляем JitPack для библиотек из GitHub
        maven { setUrl("https://jitpack.io") }
    }
}

rootProject.name = "My module"
include(":app")
