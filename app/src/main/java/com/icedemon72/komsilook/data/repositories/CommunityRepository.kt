package com.icedemon72.komsilook.data.repositories

import android.util.Log
import com.icedemon72.komsilook.data.models.Community
import com.icedemon72.komsilook.utils.Resource
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommunityRepository @Inject constructor(
	private val db: FirebaseFirestore
) {

	private fun generateRandomString(length: Int = 8): String {
		val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
		return (1..length)
			.map { characters.random() }
			.joinToString("")
	}


	suspend fun createCommunity(
		name: String,
		description: String,
		location: String,
		isPrivate: Boolean = false,
		createdBy: String
	): Resource<Community> {
		return try {
			val docRef = db.collection("communities").document()
			val id = docRef.id

			val data = hashMapOf(
				"id" to id,
				"name" to name,
				"code" to generateRandomString(),
				"description" to description,
				"location" to location,
				"private" to isPrivate,
				"createdBy" to createdBy,
				"createdAt" to FieldValue.serverTimestamp()
			)

			docRef.set(data).await()

			val memberData = mapOf("joinedAt" to FieldValue.serverTimestamp())
			docRef.collection("members").document(createdBy).set(memberData).await()

			val snapshot = docRef.get().await()
			val community = snapshot.toObject(Community::class.java)

			if (community != null) {
				Resource.Success(community)
			} else {
				Resource.Error("Greška prilikom parsiranja komšilooka")
			}

		} catch (e: Exception) {
			Resource.Error(e.localizedMessage ?: "Greška prilikom pravljenja komšilooka")
		}
	}

	suspend fun getCommunityById(id: String, userId: String?): Resource<Community> {
		return try {
			val documentSnapshot = db.collection("communities")
				.document(id)
				.get()
				.await()

			val membersSnapshot = db.collection("communities")
				.document(id)
				.collection("members")
				.document(userId!!)
				.get()
				.await()


			if (membersSnapshot.exists()) {
				val community = documentSnapshot.toObject(Community::class.java)
				Log.d("CommunityRepository", "Community Name: ${community?.name}")
				Resource.Success(community!!)
			} else {
				Resource.Error("Komšilook ne postoji!")
			}
		} catch (e: Exception) {
			Resource.Error(e.localizedMessage ?: "Greška prilikom pristupanja Komšilooku")
		}
	}

	suspend fun getNotJoinedCommunities(userId: String): Resource<List<Community>> {
		return try {
			val documentSnapshot = db.collection("communities")
				.whereEqualTo("private", false)
				.get()
				.await()

			val notJoinedCommunities = mutableListOf<Community>()

			for (document in documentSnapshot.documents) {
				val communityId = document.id
				val membersSnapshot = db.collection("communities")
					.document(communityId)
					.collection("members")
					.document(userId)
					.get()
					.await()

				if (!membersSnapshot.exists()) {
					val community = document.toObject(Community::class.java)!!
					notJoinedCommunities.add(community)
				}
			}

			Resource.Success(notJoinedCommunities)
		} catch (e: Exception) {
			Resource.Error(e.localizedMessage ?: "Greška prilikom pristupanja Komšilookcima")
		}
	}

	suspend fun getJoinedCommunities(userId: String): Resource<List<Community>> {
		return try {
			val documentSnapshot = db.collection("communities")
				.get()
				.await()

			val joinedCommunities = mutableListOf<Community>()


			for (document in documentSnapshot.documents) {
				val communityId = document.id
				val membersSnapshot = db.collection("communities")
					.document(communityId)
					.collection("members")
					.document(userId)
					.get()
					.await()

				if (membersSnapshot.exists()) {
					val community = document.toObject(Community::class.java)!!
					community.id = communityId
					joinedCommunities.add(community)
				}
			}

			Resource.Success(joinedCommunities)
		} catch (e: Exception) {
			Resource.Error(e.localizedMessage ?: "Greška prilikom pristupanja Komšilookcima")
		}
	}

	suspend fun searchCommunities(searchParam: String, userId: String): Resource<List<Community>> {
		return try {
			val snapshot = db.collection("communities")
				.whereEqualTo("private", false)
				.get()
				.await()

			val results = mutableListOf<Community>()

			for (document in snapshot.documents) {
				val community = document.toObject(Community::class.java)
				val communityId = document.id

				val memberSnapshot = db.collection("communities")
					.document(communityId)
					.collection("members")
					.document(userId)
					.get()
					.await()

				if (!memberSnapshot.exists()) {
					if (community != null && (community.name.contains(searchParam, ignoreCase = true) == true ||
								community.description.startsWith(searchParam, ignoreCase = true) == true ||
								community.location.contains(searchParam, ignoreCase = true) == true)) {
						results.add(community)
					}
				}
			}

			Resource.Success(results)
		} catch (e: Exception) {
			Resource.Error(e.localizedMessage ?: "Greška prilikom pretrage komšilooka")
		}
	}

	suspend fun joinCommunity(communityId: String, userId: String): Resource<Unit> {
		return try {
			val memberRef = db.collection("communities")
				.document(communityId)
				.collection("members")
				.document(userId)

			val memberData = mapOf(
				"joinedAt" to FieldValue.serverTimestamp()
			)

			memberRef.set(memberData).await()
			Resource.Success(Unit)
		} catch (e: Exception) {
			Resource.Error(e.message ?: "Failed to join community")
		}
	}
}