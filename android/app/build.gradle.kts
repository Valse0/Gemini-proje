import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

// Loads android/local.properties (git-ignored, machine-local) if present, so
// a developer can set `LUMA_BACKEND_URL=...` there without passing
// `-PLUMA_BACKEND_URL=...` on every Gradle invocation. Safe no-op (empty
// Properties) when the file doesn't exist, e.g. on CI.
val localProps = Properties().apply {
    val localPropsFile = rootProject.file("local.properties")
    if (localPropsFile.exists()) {
        localPropsFile.inputStream().use { load(it) }
    }
}

android {
    namespace = "com.luma.translate"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.luma.translate"
        minSdk = 26
        targetSdk = 34
        // CI (BUILD_NUMBER = GitHub Actions' github.run_number) always
        // increases, so that path was already safe. The previous local-build
        // fallback was a hardcoded `1`, meaning every local debug build had
        // the exact same versionCode: installing a second local build over a
        // first one (or over a higher-versionCode CI build) is a version
        // *downgrade*, which Android's package manager refuses
        // (INSTALL_FAILED_VERSION_DOWNGRADE) - another way the reported
        // "have to uninstall and reinstall to update" symptom could occur.
        // Minutes-since-epoch is small enough to stay well under the Play
        // versionCode ceiling (2,100,000,000) for decades, and is
        // monotonically increasing across any two local builds.
        versionCode = System.getenv("BUILD_NUMBER")?.toInt()
            ?: (System.currentTimeMillis() / 60_000L).toInt()
        versionName = "0.1.${System.getenv("BUILD_NUMBER") ?: "dev"}"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Configurable backend URL, instead of the hardcoded
        // "http://10.0.2.2:3000" (Android-emulator-only loopback alias) that
        // pipeline/TranslationPipeline.kt previously had baked in as its
        // only option. Override per build with
        // `-PLUMA_BACKEND_URL=https://your-backend.example.com`, or set it
        // in `android/local.properties` (git-ignored) as
        // `LUMA_BACKEND_URL=...`. The emulator-loopback default only works
        // for local development against a backend running on the same
        // machine as the emulator; it will not reach anything from a real
        // device. See docs/KNOWN_ISSUES.md.
        val backendUrl = (project.findProperty("LUMA_BACKEND_URL") as String?)
            ?: localProps.getProperty("LUMA_BACKEND_URL")
            ?: "http://10.0.2.2:3000"
        buildConfigField("String", "BACKEND_BASE_URL", "\"$backendUrl\"")
    }

    signingConfigs {
        // Shared, committed debug keystore (android/app/debug.keystore).
        //
        // Root cause of the reported "update crashes, I have to uninstall and
        // reinstall" bug: this project previously had no explicit debug
        // signingConfig, so Gradle fell back to its *implicit* default debug
        // config, which auto-generates a new, random `~/.android/debug.keystore`
        // on any machine (or CI runner) where one doesn't already exist. Every
        // GitHub Actions run starts on a brand-new runner with no cached
        // `~/.android` directory, so every CI-built debug APK was signed with a
        // *different* certificate. Android refuses to install an update whose
        // signing certificate doesn't match the already-installed app
        // (`INSTALL_FAILED_UPDATE_INCOMPATIBLE`), which forces an uninstall
        // before the new APK can go on. Pinning a single, checked-in debug
        // keystore makes every debug build - local or CI - share one identity,
        // so ordinary updates install over the previous build.
        //
        // This keystore is intentionally NOT a secret: it uses the exact
        // well-known placeholder credentials Android's own auto-generated
        // debug keystore uses (store/key password "android", alias
        // "androiddebugkey"). It must never be used to sign a release build,
        // and it is not used for the `release` signingConfig below.
        create("sharedDebug") {
            storeFile = file("debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }

        // Release signing is intentionally NOT committed to the repository
        // (see AGENTS.md "Git hygiene" / docs/SECURITY.md "Secrets"). It is
        // configured only when a real keystore is supplied out-of-band via
        // Gradle project properties (e.g. `-PRELEASE_STORE_FILE=...`) or
        // matching `ORG_GRADLE_PROJECT_*` / environment variables in CI. When
        // none are supplied, `assembleRelease` still succeeds but produces an
        // *unsigned* release artifact, which cannot be installed on a device
        // until it is signed - this is a safer default than silently falling
        // back to the debug key for a release build.
        if (project.hasProperty("RELEASE_STORE_FILE")) {
            create("release") {
                storeFile = file(project.property("RELEASE_STORE_FILE") as String)
                storePassword = project.property("RELEASE_STORE_PASSWORD") as String
                keyAlias = project.property("RELEASE_KEY_ALIAS") as String
                keyPassword = project.property("RELEASE_KEY_PASSWORD") as String
            }
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
            if (project.hasProperty("RELEASE_STORE_FILE")) {
                signingConfig = signingConfigs.getByName("release")
            }
        }
        debug {
            applicationIdSuffix = ".debug"
            isDebuggable = true
            signingConfig = signingConfigs.getByName("sharedDebug")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
        // Required for buildConfigField(...) above (BACKEND_BASE_URL) to
        // actually generate a BuildConfig class. Off by default since AGP 8.
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // ML Kit On-device OCR
    implementation(libs.mlkit.text)
    implementation(libs.mlkit.text.japanese)
    implementation(libs.mlkit.text.korean)
    implementation(libs.mlkit.text.chinese)

    // Backend HTTP Client
    implementation(libs.okhttp)
    implementation(libs.kotlinx.coroutines.android)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
