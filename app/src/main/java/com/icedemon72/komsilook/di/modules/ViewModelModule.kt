package com.icedemon72.komsilook.di.modules
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.icedemon72.komsilook.di.ViewModelFactory
import com.icedemon72.komsilook.di.annotations.ViewModelKey
import com.icedemon72.komsilook.ui.auth.AuthViewModel
import com.icedemon72.komsilook.ui.pages.HomeViewModel
import com.icedemon72.komsilook.ui.pages.communities.CommunitiesViewModel
import com.icedemon72.komsilook.ui.pages.communities.community.CommunityViewModel
import com.icedemon72.komsilook.ui.pages.communities.communitysettings.CommunitySettingsViewModel
import com.icedemon72.komsilook.ui.pages.communities.createcommunity.CreateCommunityViewModel
import com.icedemon72.komsilook.ui.pages.communities.joincommunity.JoinCommunityViewModel
import com.icedemon72.komsilook.ui.pages.posts.createpost.CreatePostViewModel
import com.icedemon72.komsilook.ui.pages.posts.post.PostViewModel
import com.icedemon72.komsilook.ui.pages.profile.ProfileViewModel
import com.icedemon72.komsilook.ui.pages.search.SearchViewModel
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

	@Binds
	@IntoMap
	@ViewModelKey(CommunitiesViewModel::class)
	abstract fun bindCommunitiesViewModel(viewModel: CommunitiesViewModel): ViewModel

	@Binds
	@IntoMap
	@ViewModelKey(CreatePostViewModel::class)
	abstract fun bindCreatePostViewModel(viewModel: CreatePostViewModel): ViewModel

	@Binds
	@IntoMap
	@ViewModelKey(PostViewModel::class)
	abstract fun bindPostViewModel(viewModel: PostViewModel): ViewModel

	@Binds
	@IntoMap
	@ViewModelKey(HomeViewModel::class)
	abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

	@Binds
	@IntoMap
	@ViewModelKey(SearchViewModel::class)
	abstract fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel

	@Binds
	@IntoMap
	@ViewModelKey(JoinCommunityViewModel::class)
	abstract fun bindJoinCommunityViewModel(viewModel: JoinCommunityViewModel): ViewModel

	@Binds
	@IntoMap
	@ViewModelKey(CommunitySettingsViewModel::class)
	abstract fun bindCommunitySettingsViewModel(viewModel: CommunitySettingsViewModel): ViewModel
}
