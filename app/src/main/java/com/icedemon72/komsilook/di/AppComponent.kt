package com.icedemon72.komsilook.di


import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.icedemon72.komsilook.di.modules.FirebaseModule
import com.icedemon72.komsilook.di.modules.RepositoryModule
import com.icedemon72.komsilook.di.modules.ViewModelModule
import com.icedemon72.komsilook.ui.auth.LoginFragment
import com.icedemon72.komsilook.ui.auth.RegisterFragment
import com.icedemon72.komsilook.ui.pages.communities.community.CommunityFragment
import com.icedemon72.komsilook.ui.pages.communities.createcommunity.CreateCommunityFragment
import com.icedemon72.komsilook.ui.pages.profile.ProfileFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
	RepositoryModule::class,
	ViewModelModule::class,
	FirebaseModule::class,
])
interface AppComponent {
	fun viewModelFactory(): ViewModelFactory

	@Component.Factory
	interface Factory {
		fun create(@BindsInstance application: Application): AppComponent
	}

	fun firebaseAuth(): FirebaseAuth
	fun firebaseFirestore(): FirebaseFirestore
	fun inject(fragment: LoginFragment)
	fun inject(fragment: RegisterFragment)
	fun inject(fragment: CreateCommunityFragment)
	fun inject(fragment: ProfileFragment)
	fun inject(fragment: CommunityFragment)
	// Add other inject methods for activities, services, etc.
}


