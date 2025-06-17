package com.icedemon72.komsilook.ui.pages.communities.communitysettings

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

class CommunitySettingsViewModel @Inject constructor(
    private val repository: CommunityRepository,
    auth: FirebaseAuth
): ViewModel() {
    private val userId = auth.currentUser?.uid
    private val _communityLiveData = MutableLiveData<Resource<Community>>()
    val communityLiveData: LiveData<Resource<Community>> get() = _communityLiveData

    var isCreator: Boolean = false
        private set

    fun loadCommunity(communityId: String) {
        viewModelScope.launch {
            val result = repository.getCommunityById(communityId, userId)
            if (result is Resource.Success && result.data != null) {
                isCreator = result.data.createdBy == userId
            }
            _communityLiveData.value = result
        }
    }

    fun deleteCommunity(communityId: String) {
        viewModelScope.launch {
            repository.deleteCommunity(communityId)
        }
    }

    fun leaveCommunity(communityId: String) {
        viewModelScope.launch {
            if (userId != null) {
                repository.leaveCommunity(communityId, userId)
            }
        }
    }

    fun regenerateCode(communityId: String) {
        _communityLiveData.value = Resource.Loading()
        viewModelScope.launch {
            try {
                val newCode = repository.regenerateCommunityCode(communityId)
                val updatedCommunity = repository.getCommunityById(communityId, userId)
                _communityLiveData.value = updatedCommunity
            } catch (e: Exception) {
                _communityLiveData.value = Resource.Error("Neuspešno generisanje koda")
            }
        }
    }
}