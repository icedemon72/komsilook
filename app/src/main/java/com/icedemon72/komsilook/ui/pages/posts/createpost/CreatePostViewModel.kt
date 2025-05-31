package com.icedemon72.komsilook.ui.pages.posts.createpost

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

class CreatePostViewModel @Inject constructor(
	private val repository: PostRepository,
	auth: FirebaseAuth,
): ViewModel() {
	private val userId = auth.currentUser?.uid
	private val _postState = MutableLiveData<Resource<Post>?>(null)
	val postState: LiveData<Resource<Post>?> = _postState

	fun createPost(title: String, description: String, type: String, communityId: String) {
		if (userId.isNullOrEmpty()) {
			_postState.value = Resource.Error("Korisnik nije ulogovan")
			return
		}

		viewModelScope.launch {
			_postState.value = Resource.Loading()

			val result = repository.createPost(title, description, type, communityId, userId)
			_postState.value = when (result) {
				is Resource.Success -> Resource.Success(result.data)
				is Resource.Error -> Resource.Error(result.message!!)
				is Resource.Loading -> Resource.Loading()
			}
		}
	}
}