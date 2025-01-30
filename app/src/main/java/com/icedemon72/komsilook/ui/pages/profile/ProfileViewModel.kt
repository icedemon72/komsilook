package com.icedemon72.komsilook.ui.pages.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icedemon72.komsilook.data.models.User
import com.icedemon72.komsilook.data.repositories.AuthRepository
import com.icedemon72.komsilook.utils.Resource

class ProfileViewModel (private val authRepository: AuthRepository) : ViewModel() {
	fun getCurrentUser(): User? {
		return authRepository.getCurrentUser()
	}

	fun logout() {
		authRepository.logout()
	}
}