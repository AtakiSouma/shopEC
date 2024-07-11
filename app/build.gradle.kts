plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.online_shop_app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.online_shop_app"
        minSdk = 24
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
    buildFeatures {
        viewBinding =  true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.glide)
    implementation(libs.gson)
    implementation ("com.squareup.retrofit2:retrofit:2.5.0");
    implementation("com.squareup.retrofit2:converter-gson:2.4.0")
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    implementation(libs.firebase.messaging)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(platform("com.google.firebase:firebase-bom:33.1.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.android.gms:play-services-auth:21.2.0")

    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.12.0"))

    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor")
    implementation("com.github.denzcoskun:ImageSlideshow:0.1.2")
    // sekeleton effect
    implementation("com.facebook.shimmer:shimmer:0.1.0@aar")






    // map box

    implementation("com.mapbox.maps:android:11.4.1")
    implementation("com.mapbox.navigationcore:navigation:3.2.0-beta.1");
    implementation("com.mapbox.navigationcore:copilot:3.2.0-beta.1");
    implementation("com.mapbox.navigationcore:ui-maps:3.2.0-beta.1");
    implementation("com.mapbox.navigationcore:voice:3.2.0-beta.1");
    implementation("com.mapbox.navigationcore:android:3.2.0-beta.1");
    implementation("com.mapbox.navigationcore:ui-components:3.2.0-beta.1")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    // Socket.IO client dependency with exclusion
    implementation("io.socket:socket.io-client:2.0.0") {
        exclude(group = "org.json", module = "json")
    }









}