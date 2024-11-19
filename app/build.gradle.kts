plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.mushroomhunting"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mushroomhunting"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation ("com.google.android.gms:play-services-location:18.0.0")
    implementation ("com.google.android.material:material:1.9.0")
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.lombok)
    implementation(libs.play.services.location)
    implementation(libs.recyclerview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}