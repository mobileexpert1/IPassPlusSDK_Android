// Top-level build file where you can add configuration options common to all sub-projects/modules.
/*allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven {
            url = uri("https://maven.regulaforensics.com/RegulaDocumentReader")
        }
        flatDir {
            dirs ("libs")
        }
    }
}*/

plugins {
    id("com.android.application") version "8.1.4" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
//    id("org.jetbrains.kotlin.android") version "1.6.10" apply false
    id("com.android.library") version "8.1.1" apply false
}