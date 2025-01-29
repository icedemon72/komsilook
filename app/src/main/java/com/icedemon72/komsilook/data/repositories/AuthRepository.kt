package com.icedemon72.komsilook.data.repositories

import com.icedemon72.komsilook.data.models.User
import com.icedemon72.komsilook.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class AuthRepository {

    private val auth: FirebaseAuth = Firebase.auth

    suspend fun registerWithEmail(email: String, password: String, name: String): Resource<AuthResult> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            result.user?.let { user ->
                // Update profile with display name and photo URL
                val profileUpdates = userProfileChangeRequest {
                    displayName = name
                }
                user.updateProfile(profileUpdates).await()
            }
            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Registration failed")
        }
    }

    suspend fun loginWithEmail(email: String, password: String): Resource<AuthResult> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Login failed")
        }
    }

    suspend fun loginWithGoogle(idToken: String): Resource<AuthResult> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val result = auth.signInWithCredential(credential).await()
            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Google login failed")
        }
    }

    fun logout() {
        auth.signOut()
    }

    fun getCurrentUser(): User? {
        val currentUser = auth.currentUser
        return currentUser?.let {
            User(it.uid, it.email, it.displayName)
        }
    }
}
