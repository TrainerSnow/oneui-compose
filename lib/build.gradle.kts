@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    id("maven-publish")
}

android {
    namespace = "org.oneui.compose"
    compileSdk = 34

    defaultConfig {
        minSdk = 23
        version = 1

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
}

dependencies {
    implementation(platform(libs.compose.bom))

    implementation(libs.compose.material3)
    implementation(libs.compose.material)
    implementation(libs.compose.foundation)
    implementation(libs.compose.ui)
    implementation(libs.compose.animation)

    debugRuntimeOnly(libs.compose.ui.test.manifest)
    implementation(libs.io.github.oneuiproject.icons)

    implementation(libs.androidx.annotation)
    implementation(libs.androidx.core)
    implementation(libs.jetbrains.kotlinx.coroutines.core)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = "com.github.TrainerSnow"
                artifactId = "oneui-compose"
                version = "0.1.1-hf3"

                artifact(tasks.getByName("bundleReleaseAar"))
            }
        }
    }
}