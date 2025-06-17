package com.icedemon72.komsilook.ui.pages.search

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

class SearchViewModel @Inject constructor(
	private val repository: CommunityRepository,
	auth: FirebaseAuth
): ViewModel() {
	private val userId = auth.currentUser?.uid

	private val _communitiesState =  MutableLiveData<Resource<List<Community>>>()
	val communitiesState: LiveData<Resource<List<Community>>> = _communitiesState

	fun searchCommunities(searchTerm: String = "") {
		viewModelScope.launch {
			_communitiesState.value = Resource.Loading()
			val result = repository.searchCommunities(searchTerm, userId!!)
			_communitiesState.value = result
		}
	}

	fun getCommunities() {
		viewModelScope.launch {
			_communitiesState.value = Resource.Loading()
			val result = repository.getNotJoinedCommunities(userId!!)
			_communitiesState.value = result
		}
	}

	fun joinCommunity(community: Community) {
		viewModelScope.launch {
			repository.joinCommunity(community.id!!, userId!!)
		}
	}
}