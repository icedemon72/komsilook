package com.icedemon72.komsilook.ui.pages.communities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.icedemon72.komsilook.data.models.Community
import com.icedemon72.komsilook.data.repositories.CommunityRepository
import com.icedemon72.komsilook.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

class CommunitiesViewModel @Inject constructor(
	private val repository: CommunityRepository,
	auth: FirebaseAuth
) : ViewModel() {
	private val userId = auth.currentUser?.uid
	private val _communitiesState = MutableLiveData<Resource<List<Community>>?>()
	val communitiesState: LiveData<Resource<List<Community>>?> = _communitiesState

	fun getCommunities() {
		viewModelScope.launch {
			_communitiesState.value = Resource.Loading()

			val result = repository.getJoinedCommunities(userId!!)
			_communitiesState.value = when (result) {
				is Resource.Success -> Resource.Success(result.data)
				is Resource.Error -> Resource.Error(result.message!!)
				is Resource.Loading -> Resource.Loading()
			}
		}
	}
}