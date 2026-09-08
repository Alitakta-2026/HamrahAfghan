plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}
android {
    namespace="com.hamrahafghan.app"
    compileSdk=36
    defaultConfig {
        applicationId="com.hamrahafghan.app"
        minSdk=24
        targetSdk=36
        versionCode=6
        versionName="6.0"
    }
}
dependencies {
    implementation("androidx.core:core-ktx:1.17.0")
    implementation("androidx.activity:activity-compose:1.10.1")
    implementation("androidx.compose.ui:ui:1.9.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.9.0")
    implementation("androidx.compose.material3:material3:1.3.2")
}
