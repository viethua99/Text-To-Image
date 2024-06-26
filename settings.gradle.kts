pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "TextToImage"
include(":app")
include(":feature:generate")
include(":feature:gallery")
include(":feature:settings")
include(":feature:loading")
include(":feature:result")
include(":core:network")
include(":core:database")
include(":core:data")
include(":core:model")
include(":core:domain")
include(":core:designsystem")
include(":core:ui")
include(":core:testing")
include(":core:datastore")
include(":core:common")
include(":ui-test-hilt-manifest")
include(":core:datastore-test")
