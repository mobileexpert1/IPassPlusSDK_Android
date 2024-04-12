pluginManagement {
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
        jcenter()
        maven {
            url =uri("https://maven.regulaforensics.com/RegulaDocumentReader")
        }
    }
}
//

//pluginManagement {
//    repositories {
//        gradlePluginPortal()
//        mavenLocal()
//        google()
//        mavenCentral()
//
//    }
//}
//dependencyResolutionManagement {
//    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
//    repositories {
//        google()
//        mavenCentral()
//    }
//}

rootProject.name = "IPassPlus"
include(":app")
include(":IPassPlusSdk")
