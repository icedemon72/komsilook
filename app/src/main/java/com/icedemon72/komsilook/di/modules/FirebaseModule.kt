package com.icedemon72.komsilook.di.modules

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object FirebaseModule {

	@Provides
	@Singleton
	fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

	@Provides
	@Singleton
	fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()
}