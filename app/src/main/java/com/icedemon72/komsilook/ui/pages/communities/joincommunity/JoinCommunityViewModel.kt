package com.icedemon72.komsilook.ui.pages.communities.joincommunity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.icedemon72.komsilook.data.models.Community
import com.icedemon72.komsilook.data.repositories.CommunityRepository
import com.icedemon72.komsilook.utils.Resource
import javax.inject.Inject
import kotlinx.coroutines.launch

class JoinCommunityViewModel @Inject constructor(
    private val repository: CommunityRepository,
    auth: FirebaseAuth
): ViewModel() {
    private val userId = auth.currentUser?.uid

    private val _communitiesState =  MutableLiveData<Resource<Community>>()
    val communitiesState: LiveData<Resource<Community>> = _communitiesState

    fun joinCommunityByCode(code: String) {
        viewModelScope.launch {
            _communitiesState.value = Resource.Loading()
            val result = repository.joinCommunityByCode(code, userId!!)
            _communitiesState.value = result
        }
    }
}