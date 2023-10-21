@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.android.application.get().pluginId)
    id(libs.plugins.android.navigation.safeargs.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.parcelize.get().pluginId)
    id(libs.plugins.kapt.get().pluginId)
    id(libs.plugins.ksp.get().pluginId)
}

android {
    defaultConfig {
        applicationId = "com.example.weather_app"
        minSdk = 21
        compileSdk = 34
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    buildTypes {
        val keyOpenWeatherApi = ""
        val keyPixabeyApi = ""
        debug {
            buildConfigField("String", "API_KEY_OPEN_WEATHER_MAP", "\"$keyOpenWeatherApi\"")
            buildConfigField("String", "BASE_URL_OPEN_WEATHER_MAP", "\"https://api.openweathermap.org/data/2.5/\"")
            buildConfigField("String", "BASE_URL_OPEN_WEATHER_MAP_FOR_ICONS", "\"https://openweathermap.org/img/wn/\"")
            buildConfigField("String", "API_KEY_PIXABAY", "\"$keyPixabeyApi\"")
            buildConfigField("String", "BASE_URL_PIXABAY", "\"https://pixabay.com/api/\"")
        }
        release {
            buildConfigField("String", "API_KEY_OPEN_WEATHER_MAP", "\"$keyOpenWeatherApi\"")
            buildConfigField("String", "BASE_URL_OPEN_WEATHER_MAP", "\"https://api.openweathermap.org/data/2.5/\"")
            buildConfigField("String", "BASE_URL_OPEN_WEATHER_MAP_FOR_ICONS", "\"https://openweathermap.org/img/wn/\"")
            buildConfigField("String", "API_KEY_PIXABAY", "\"$keyPixabeyApi\"")
            buildConfigField("String", "BASE_URL_PIXABAY", "\"https://pixabay.com/api/\"")
            isMinifyEnabled = false
            setProguardFiles(listOf("proguard-android-optimize.txt", "proguard-rules.pro"))
        }
    }
    dataBinding {
        enable = true
    }
    namespace = "com.example.weather_app"
}

dependencies {
    implementation(libs.android.lifecycle.viewmodel.ktx)
    implementation(libs.android.core.ktx)
    implementation(libs.livedata.ktx)
    implementation(libs.app.compat)
    implementation(libs.constraint.layout)
    implementation(libs.kotlin.coroutines)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    implementation(libs.retrofit)
    implementation(libs.retrofit.gson.converter)
    implementation(libs.okhttp)
    implementation(libs.okhttp.interceptor)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    implementation(libs.glide)

    implementation(libs.koin)
    implementation(libs.koin.android)

    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.android)
    androidTestImplementation(libs.espresso)
}
