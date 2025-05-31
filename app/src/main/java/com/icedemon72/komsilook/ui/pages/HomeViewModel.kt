package com.icedemon72.komsilook.ui.pages

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.icedemon72.komsilook.data.models.Post
import com.icedemon72.komsilook.data.repositories.PostRepository
import com.icedemon72.komsilook.utils.Resource
import javax.inject.Inject
import kotlinx.coroutines.launch

class HomeViewModel @Inject constructor(
	private val repository: PostRepository,
	auth: FirebaseAuth
): ViewModel() {
	private val userId = auth.currentUser?.uid

	private val _postsState = MutableLiveData<Resource<List<Post>>>()
	val postsState: LiveData<Resource<List<Post>>> = _postsState

	fun getPostsInJoinedCommunities() {
		viewModelScope.launch {
			_postsState.value = Resource.Loading()
			val result = repository.getAllPostsForUserCommunities(userId!!)
			_postsState.value = result
		}
	}
}