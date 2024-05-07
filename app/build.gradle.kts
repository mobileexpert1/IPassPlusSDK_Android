plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-parcelize")
}

android {
    namespace = "com.app.ipassplus"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.app.ipassplus"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        resConfigs("en")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        //isCoreLibraryDesugaringEnabled = true
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

    val kotlin_version= "1.9.22"
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version")
    implementation ("com.regula.documentreader.core:fullauthrfid:7.2.10816@aar")

    implementation(project(mapOf("path" to ":IPassPlusSdk")))
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
//    implementation("androidx.compose.ui:ui")
//    implementation("androidx.compose.ui:ui-graphics")
//    implementation("androidx.compose.ui:ui-tooling-preview")
//    implementation("androidx.compose.material3:material3")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    implementation("com.google.android.play:feature-delivery-ktx:2.1.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation ("com.intuit.sdp:sdp-android:1.1.0")
//    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation("com.tbuonomo:dotsindicator:5.0")
    implementation ("androidx.viewpager2:viewpager2:1.0.0")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
//    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.6")
    implementation ("com.google.code.gson:gson:2.8.9")

}