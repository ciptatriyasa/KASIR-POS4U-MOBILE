plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.pos4u_mobile"
    compileSdk = 36 // Sederhanakan penulisan compileSdk agar stabil

    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.example.pos4u_mobile"
        minSdk = 26
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // Dependency bawaan template (menggunakan versi Version Catalog / libs.toml)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Jaringan & API (Retrofit & OkHttp) - DIUBAH KE FORMAT KOTLIN DSL YANG BENAR ("...")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    // Lifecycle untuk Java (ViewModel & LiveData tanpa ekstensi -ktx Kotlin)
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata:2.6.2")
}