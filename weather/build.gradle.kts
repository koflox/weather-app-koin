@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id(libs.plugins.android.library.get().pluginId)
    alias(libs.plugins.kotlin.android)
    id(libs.plugins.kapt.get().pluginId)
    id(libs.plugins.ksp.get().pluginId)
    id(libs.plugins.android.navigation.safeargs.get().pluginId)
}

android {
    namespace = "com.koflox.weather"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
        targetSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    dataBinding {
        enable = true
    }
}

dependencies {
    implementation(project(":common-ui"))
    implementation(project(":common-android-res"))
    implementation(project(":common-jvm-util"))

    implementation(libs.android.lifecycle.viewmodel.ktx)
    implementation(libs.android.core.ktx)
    implementation(libs.app.compat)
    implementation(libs.constraint.layout)
    implementation(libs.kotlin.coroutines)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.databinding.runtime)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson.converter)
    implementation(libs.okhttp)
    implementation(libs.okhttp.interceptor)
    implementation(libs.koin)
    implementation(libs.koin.android)
    implementation(libs.glide)
}
