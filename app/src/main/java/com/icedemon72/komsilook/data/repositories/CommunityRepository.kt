package com.icedemon72.komsilook.data.repositories

import com.google.firebase.ktx.Firebase
import com.icedemon72.komsilook.data.models.Community
import com.icedemon72.komsilook.utils.Resource
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommunityRepository @Inject constructor(
	private val db: FirebaseFirestore
) {

	suspend fun createCommunity(name: String, description: String, location: String, isPrivate: Boolean = false, createdBy: String): Resource<Community> {
		var community = Community(
			name = name,
			description = description,
			location = location,
			isPrivate = isPrivate,
			createdBy = createdBy,
			createdAt = FieldValue.serverTimestamp()
		)

		return try {
			val documentRef = db.collection("communities")
				.add(community)
				.await()

			community.uid = documentRef.id

			Resource.Success(community)

		} catch (e: Exception) {
			Resource.Error(e.localizedMessage ?: "Greška prilikom pravljenja komšilooka")
		}
	}

}