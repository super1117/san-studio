plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.pdk.re.specified"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.pdk.re.specified"
        minSdk = 24
        targetSdk = 33
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    //noinspection GradleCompatible
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("com.google.android.gms:play-services-basement:18.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("com.github.Justson.AgentWeb:agentweb-core:v5.0.6-androidx")
    implementation ("com.github.Justson.AgentWeb:agentweb-filechooser:v5.0.6-androidx")
    implementation ("com.github.Justson:Downloader:v5.0.4-androidx")
    implementation("com.appsflyer:af-android-sdk:6.5.2")
    implementation ("com.squareup.okio:okio:2.1.0")
    implementation ("com.squareup.okhttp3:okhttp:3.12.0")
    implementation ("de.hdodenhof:circleimageview:2.1.0")
    implementation ("com.alibaba:fastjson:1.2.51")
    implementation ("com.facebook.fresco:fresco:1.9.0")
    implementation ("org.apache.commons:commons-lang3:3.8.1")
    implementation ("com.yanzhenjie:recyclerview-swipe:1.1.4")
    implementation ("com.github.bumptech.glide:glide:4.0.0")
    implementation ("com.youth.banner:banner:1.4.10")
    implementation ("com.orhanobut:dialogplus:1.11@aar")
    implementation ("com.nostra13.universalimageloader:universal-image-loader:1.9.3")
    implementation ("com.lidroid.xutils:xutils:2.6.13")
    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))

    // Add the dependencies for the Remote Config and Analytics libraries
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-config-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
}