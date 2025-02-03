package com.icedemon72.komsilook.ui.pages.communities.createcommunity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.icedemon72.komsilook.data.models.Community
import com.icedemon72.komsilook.data.repositories.CommunityRepository
import com.icedemon72.komsilook.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateCommunityViewModel @Inject constructor(
	private val repository: CommunityRepository,
	private val auth: FirebaseAuth
): ViewModel() {

	private val _communityState = MutableLiveData<Resource<Community>?>(null)
	val communityState: LiveData<Resource<Community>?> = _communityState

	fun create(name: String, description: String, location: String, isPrivate: Boolean = false) {
		val createdBy = auth.currentUser?.uid

		if (createdBy == null) {
			_communityState.value = Resource.Error("User is not authenticated.")
			return
		}

		Log.d("CreateCommunity", "Creating community with user ID: $createdBy")

		viewModelScope.launch {
			_communityState.value = Resource.Loading()

			val result = repository.createCommunity(name, description, location, isPrivate, createdBy!!)
			Log.d("CreateCommunity", result.data.toString())
			_communityState.value = when (result) {
				is Resource.Success -> Resource.Success(result.data)
				is Resource.Error -> Resource.Error(result.message!!)
				is Resource.Loading -> Resource.Loading()
			}
		}
	}
}