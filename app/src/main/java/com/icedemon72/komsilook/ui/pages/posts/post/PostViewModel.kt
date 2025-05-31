package com.icedemon72.komsilook.ui.pages.posts.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.icedemon72.komsilook.data.models.Post
import com.icedemon72.komsilook.data.repositories.PostRepository
import com.icedemon72.komsilook.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

class PostViewModel @Inject constructor(
	private val repository: PostRepository,
	auth: FirebaseAuth
): ViewModel() {
	private val userId = auth.currentUser?.uid

	private val _postState = MutableLiveData<Resource<Post>>()
	val postState: LiveData<Resource<Post>> = _postState

	fun getPost(communityId: String, id: String) {
		viewModelScope.launch {
			_postState.value = Resource.Loading()
			val result = repository.getPostInCommunity(communityId, id)
			_postState.value = result
		}
	}
}