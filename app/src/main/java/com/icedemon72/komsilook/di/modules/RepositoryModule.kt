package com.icedemon72.komsilook.di.modules

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.icedemon72.komsilook.data.repositories.AuthRepository
import com.icedemon72.komsilook.data.repositories.CommunityRepository
import com.icedemon72.komsilook.data.repositories.PostRepository
import dagger.Module
import dagger.Provides

@Module
object RepositoryModule {

	@Provides
	fun provideCommunityRepository(firebaseFirestore: FirebaseFirestore): CommunityRepository {
		return CommunityRepository(firebaseFirestore)
	}

	@Provides
	fun provideAuthRepository(auth: FirebaseAuth): AuthRepository {
		return AuthRepository(auth)
	}

	@Provides
	fun providePostRepository(firebaseFirestore: FirebaseFirestore): PostRepository {
		return PostRepository(firebaseFirestore)
	}
}