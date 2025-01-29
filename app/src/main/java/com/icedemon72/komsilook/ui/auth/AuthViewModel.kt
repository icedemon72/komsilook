package com.icedemon72.komsilook.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icedemon72.komsilook.data.models.User
import com.icedemon72.komsilook.data.repositories.AuthRepository
import com.icedemon72.komsilook.utils.Resource
import kotlinx.coroutines.launch

class AuthViewModel (private val repository: AuthRepository): ViewModel() {
    private val _authState = MutableLiveData<Resource<User>?>(null);
    val authState: LiveData<Resource<User>?> = _authState;

    init {
        // Check if a user is already logged in on ViewModel initialization
        checkCurrentUser()
    }


    private fun checkCurrentUser() {
        val currentUser = repository.getCurrentUser()
        if (currentUser != null) {
            // User is already logged in
            _authState.value = Resource.Success(currentUser)
        } else {
            // No user is logged in
            _authState.value = null
        }
    }

    fun register(email: String, password: String, displayName: String) {
        viewModelScope.launch {
            _authState.value = Resource.Loading()
            val result = repository.registerWithEmail(email, password, displayName)
            _authState.value = when (result) {
                is Resource.Success -> Resource.Success(
                    User(
                        result.data!!.user?.uid,
                        result.data.user?.email,
                        result.data.user?.displayName,
                    )
                )
                is Resource.Error -> Resource.Error(result.message!!)
                is Resource.Loading -> Resource.Loading()
            }
        }
    }


    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = Resource.Loading()
            val result = repository.loginWithEmail(email, password)

            _authState.value = when (result) {
                is Resource.Success -> Resource.Success(User(result.data!!.user?.uid, result.data.user?.email))
                is Resource.Error -> Resource.Error(result.message!!)
                is Resource.Loading -> Resource.Loading()
            }
        }
    }

    fun loginWithGoogle(idToken: String) {
        viewModelScope.launch {
            _authState.value = Resource.Loading()
            val result = repository.loginWithGoogle(idToken)

            _authState.value = when (result) {
                is Resource.Success -> Resource.Success(User(result.data!!.user?.uid, result.data.user?.email))
                is Resource.Error -> Resource.Error(result.message!!)
                is Resource.Loading -> Resource.Loading()
            }
        }
    }



    fun logout() {
        repository.logout();
        _authState.value = null // Clear the authentication state
    }
}
