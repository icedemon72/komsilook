package com.icedemon72.komsilook.ui.pages.communities

import android.util.Log
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
	private val auth: FirebaseAuth
) : ViewModel() {
	private val userId = auth.currentUser?.uid
	private val _communitiesState = MutableLiveData<Resource<List<Community>>?>()
	val communitiesState: LiveData<Resource<List<Community>>?> = _communitiesState

	fun getNotJoined() {
		viewModelScope.launch {
			_communitiesState.value = Resource.Loading()

			val result = repository.getNotJoinedCommunities(userId!!)
			_communitiesState.value = when (result) {
				is Resource.Success -> Resource.Success(result.data)
				is Resource.Error -> Resource.Error(result.message!!)
				is Resource.Loading -> Resource.Loading()
			}
		}
	}
}