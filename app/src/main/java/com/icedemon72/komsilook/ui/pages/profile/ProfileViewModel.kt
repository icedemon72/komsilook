package com.icedemon72.komsilook.ui.pages.profile

import androidx.lifecycle.ViewModel
import com.icedemon72.komsilook.data.models.User
import com.icedemon72.komsilook.data.repositories.AuthRepository
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
	private val authRepository: AuthRepository
) : ViewModel() {

	fun getCurrentUser(): User? {
		return authRepository.getCurrentUser()
	}

	fun logout() {
		authRepository.logout()
	}
}