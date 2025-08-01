plugins {
   alias(libs.plugins.android.application)
   alias(libs.plugins.google.gms.google.services)
}

android {
   namespace = "ca.georgiancollege.assignment_01"
   compileSdk = 35

   defaultConfig {
      applicationId = "ca.georgiancollege.assignment_01"
      minSdk = 24
      targetSdk = 35
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
      viewBinding = true
   }

   compileOptions {
      sourceCompatibility = JavaVersion.VERSION_11
      targetCompatibility = JavaVersion.VERSION_11
   }
}

dependencies {

   implementation(libs.appcompat)
   implementation(libs.material)
   implementation(libs.activity)
   implementation(libs.constraintlayout)
   implementation(libs.firebase.auth)
   testImplementation(libs.junit)
   androidTestImplementation(libs.ext.junit)
   androidTestImplementation(libs.espresso.core)
}