plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.kotlin.android)
	alias(libs.plugins.google.gms.google.services)
	alias(libs.plugins.ksp)
	id("androidx.navigation.safeargs.kotlin") version "2.7.7"
}

android {
	namespace = "com.icedemon72.komsilook"
	compileSdk = 35

	defaultConfig {
		applicationId = "com.icedemon72.komsilook"
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
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}
	kotlinOptions {
		jvmTarget = "11"
	}
	buildFeatures {
		//noinspection DataBindingWithoutKapt
		dataBinding = true
	}
}

dependencies {

	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.appcompat)
	implementation(libs.material)
	implementation(libs.androidx.activity)
	implementation(libs.androidx.constraintlayout)

	implementation(libs.androidx.lifecycle.viewmodel.ktx)
	implementation(libs.androidx.navigation.fragment.ktx)
	implementation(libs.androidx.navigation.ui.ktx)
	implementation(libs.androidx.activity)
	implementation(libs.androidx.constraintlayout)
	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.appcompat)
	implementation(libs.firebase.auth)
	implementation(libs.androidx.legacy.support.v4)
	implementation(libs.androidx.lifecycle.livedata.ktx)
	implementation(libs.androidx.fragment.ktx)
	implementation(libs.firebase.firestore)

	// Tests
	testImplementation(libs.junit)
	androidTestImplementation(libs.androidx.junit)
	androidTestImplementation(libs.androidx.espresso.core)

	// Dagger
	implementation(libs.dagger)
	ksp(libs.dagger.compiler)

	// Dagger Android dependencies
	implementation(libs.dagger.android)
	ksp(libs.dagger.android.processor)
}