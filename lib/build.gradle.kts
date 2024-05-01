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
        isCoreLibraryDesugaringEnabled = true
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(platform(libs.compose.bom))

    implementation(libs.compose.material3)
    implementation(libs.compose.material3.windowsizeclas)
    implementation(libs.compose.material)
    implementation(libs.compose.foundation)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.preview)
    implementation(libs.compose.animation)
    debugImplementation(libs.androidx.ui.tooling)

    debugRuntimeOnly(libs.compose.ui.test.manifest)
    implementation(libs.io.github.oneuiproject.icons)

    implementation(libs.androidx.annotation)
    implementation(libs.androidx.core)
    implementation(libs.jetbrains.kotlinx.coroutines.core)

    coreLibraryDesugaring(libs.coreLibDesugaring)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = "com.github.TrainerSnow"
                artifactId = "oneui-compose"
                version = "0.5.5"

                artifact(tasks.getByName("bundleReleaseAar"))
            }
        }
    }
}