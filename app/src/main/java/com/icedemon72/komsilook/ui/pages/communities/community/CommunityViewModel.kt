package com.icedemon72.komsilook.ui.pages.communities.community

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.google.firebase.auth.FirebaseAuth
import com.icedemon72.komsilook.data.models.Community
import com.icedemon72.komsilook.data.models.Post
import com.icedemon72.komsilook.data.repositories.CommunityRepository
import com.icedemon72.komsilook.data.repositories.PostRepository
import com.icedemon72.komsilook.utils.Resource
import javax.inject.Inject

class CommunityViewModel @Inject constructor(
	private val repository: CommunityRepository,
	private val postRepository: PostRepository,
	auth: FirebaseAuth
) : ViewModel() {

	private val userId = auth.currentUser?.uid

	private val _communitiesState = MutableLiveData<Resource<Community>?>()
	val communitiesState: LiveData<Resource<Community>?> = _communitiesState

	private val _postsState = MutableLiveData<Resource<List<Post>>>()
	val postsState: LiveData<Resource<List<Post>>> = _postsState

	fun getCommunity(id: String) {
		viewModelScope.launch {
			_communitiesState.value = Resource.Loading()
			val result = repository.getCommunityById(id, userId)
			_communitiesState.value = result
		}
	}

	fun getPostsInCommunity(communityId: String) {
		viewModelScope.launch {
			_postsState.value = Resource.Loading()
			val result = postRepository.getPostsInCommunity(communityId)
			_postsState.value = result
		}
	}
}
