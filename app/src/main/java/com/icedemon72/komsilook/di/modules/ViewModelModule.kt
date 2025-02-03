package com.icedemon72.komsilook.di.modules
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.icedemon72.komsilook.di.ViewModelFactory
import com.icedemon72.komsilook.di.annotations.ViewModelKey
import com.icedemon72.komsilook.ui.auth.AuthViewModel
import com.icedemon72.komsilook.ui.pages.communities.community.CommunityViewModel
import com.icedemon72.komsilook.ui.pages.communities.createcommunity.CreateCommunityViewModel
import com.icedemon72.komsilook.ui.pages.profile.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

	@Binds
	abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

	@Binds
	@IntoMap
	@ViewModelKey(AuthViewModel::class)
	abstract fun bindAuthViewModel(viewModel: AuthViewModel): ViewModel

	@Binds
	@IntoMap
	@ViewModelKey(CreateCommunityViewModel::class)
	abstract fun bindCreateCommunityViewModel(viewModel: CreateCommunityViewModel): ViewModel

	@Binds
	@IntoMap
	@ViewModelKey(ProfileViewModel::class)
	abstract fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel

	@Binds
	@IntoMap
	@ViewModelKey(CommunityViewModel::class)
	abstract fun bindCommunityViewModel(viewModel: CommunityViewModel): ViewModel
}
