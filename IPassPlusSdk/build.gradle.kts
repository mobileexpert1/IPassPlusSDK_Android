plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
//    id("com.google.gms.google-services")

}

var artifactId = "IPassPlusSDK"
var groupId = "com.sdk.ipassplussdk"

android {
    namespace = "com.sdk.ipassplussdk"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        resConfigs("en")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
//        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
    packagingOptions {
        exclude("AndroidManifest.xml")
        exclude("lib/arm64-v8a/libcardioDecider.so")
        exclude("lib/arm64-v8a/libcardioRecognizer.so")
        exclude("lib/arm64-v8a/libcardioRecognizer_tegra2.so")
        exclude("lib/arm64-v8a/libopencv_core.so")
        exclude("lib/arm64-v8a/libopencv_imgproc.so")
        exclude("lib/armeabi/libcardioDecider.so")
        exclude("lib/armeabi-v7a/libcardioDecider.so")
        exclude("lib/armeabi-v7a/libcardioRecognizer.so")
        exclude("lib/armeabi-v7a/libcardioRecognizer_tegra2.so")
        exclude("lib/armeabi-v7a/libopencv_core.so")
        exclude("lib/armeabi-v7a/libopencv_imgproc.so")
        exclude("lib/mips/libcardioDecider.so")
        exclude("lib/x86/libcardioDecider.so")
        exclude("lib/x86/libcardioRecognizer.so")
        exclude("lib/x86/libcardioRecognizer_tegra2.so")
        exclude("lib/x86/libopencv_core.so")
        exclude("lib/x86/libopencv_imgproc.so")
        exclude("lib/x86_64/libcardioDecider.so")
        exclude("lib/x86_64/libcardioRecognizer.so")
        exclude("lib/x86_64/libcardioRecognizer_tegra2.so")
        exclude("lib/x86_64/libopencv_core.so")
        exclude("lib/x86_64/libopencv_imgproc.so")
    }

}

dependencies {
//    coreLibraryDesugaring ("com.android.tools:desugar_jdk_libs:1.0.5")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

//    implementation("com.google.android.play:feature-delivery-ktx:2.1.0")

    implementation("com.regula.documentreader:api:7.2.9754+@aar") {
        this.isTransitive = true
    }
//    implementation ("com.regula.documentreader.core:fullauthrfid:7.2.10816@aar")
//    implementation(files("libs/api-6.9.1398"))


    // FaceLivenessDetector dependency
    implementation ("com.amplifyframework.ui:liveness:1.2.1")

    // Amplify Auth dependency (unnecessary if using your own credentials provider)
    implementation ("com.amplifyframework:aws-auth-cognito:2.14.5")

    // Material3 dependency for theming FaceLivenessDetector
    implementation ("androidx.compose.material3:material3:1.2.0")

    // Support for Java 8 features
    coreLibraryDesugaring ("com.android.tools:desugar_jdk_libs:1.1.5")

    implementation ("com.amazonaws:aws-android-sdk-core:2.16.0")
    implementation ("com.amazonaws:aws-android-sdk-rekognition:2.16.0")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.6")
    implementation ("com.google.code.gson:gson:2.8.9")
    implementation ("com.google.android.gms:play-services-vision:20.1.3")



}
project.afterEvaluate {
    publishing {
        publications {
            // Creates a Maven publication called "release".
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = "com.sdk.ipassplussdk"
                artifactId = "IPassPlusSDK"
                version = "2.0.12"
            }
        }
    }
}