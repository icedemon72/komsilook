package com.icedemon72.komsilook.data.repositories

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.icedemon72.komsilook.data.models.Post
import com.icedemon72.komsilook.utils.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepository @Inject constructor(
	private val db: FirebaseFirestore
) {
	suspend fun createPost(
		title: String,
		description: String,
		type: String,
		communityId: String,
		createdBy: String
	): Resource<Post> {
		return try {
			val docRef = db.collection("communities")
				.document(communityId)
				.collection("posts")
				.document()

			val data = mapOf(
				"id" to docRef.id,
				"title" to title,
				"description" to description,
				"type" to type,
				"createdAt" to FieldValue.serverTimestamp(),
				"createdBy" to createdBy,
				"communityId" to docRef.id
			)

			docRef.set(data).await()

			val snapshot = docRef.get().await()
			val post = snapshot.toObject(Post::class.java)

			Resource.Success(post)
		} catch (e: Exception) {
			Resource.Error(e.localizedMessage ?: "Greška prilikom pravljenja komšilooka")
		}
	}

	suspend fun getPostsInCommunity(communityId: String): Resource<List<Post>> {
		return try {
			val snapshot = db.collection("communities")
				.document(communityId)
				.collection("posts")
				.orderBy("createdAt", Query.Direction.DESCENDING)
				.get()
				.await()

			val posts = snapshot.toObjects(Post::class.java)
			Resource.Success(posts)
		} catch (e: Exception) {
			Resource.Error(e.localizedMessage ?: "Greška prilikom uzimanja objava u komšilooku")
		}
	}

	suspend fun getPostInCommunity(
		communityId: String, postId: String
	): Resource<Post> {
		return try {
			val snapshot = db.collection("communities")
				.document(communityId)
				.collection("posts")
				.document(postId)
				.get()
				.await()

			if (snapshot.exists()) {
				val post = snapshot.toObject(Post::class.java)
				return Resource.Success(post)
			}

			throw Exception("Ova objava ne postoji")

		} catch (e: Exception) {
			Resource.Error(e.localizedMessage ?: "Greška prilikom uzimanja objava u komšilooku")
		}
	}

	suspend fun getAllPostsForUserCommunities(userId: String): Resource<List<Post>> {
		return try {
			val communitiesSnapshot = db.collection("communities").get().await()
			val communityIds = mutableListOf<String>()

			for (community in communitiesSnapshot.documents) {
				val memberSnapshot = community.reference
					.collection("members")
					.document(userId)
					.get()
					.await()

				if (memberSnapshot.exists()) {
					communityIds.add(community.id)
				}
			}

			val allPosts = mutableListOf<Post>()
			for (communityId in communityIds) {
				val postsSnapshot = db.collection("communities")
					.document(communityId)
					.collection("posts")
					.get()
					.await()

				val posts = postsSnapshot.mapNotNull { doc ->
					doc.toObject(Post::class.java).copy(communityId = communityId)
				}
				allPosts.addAll(posts)
			}

			val sortedPosts = allPosts.sortedByDescending { it.createdAt }

			Resource.Success(sortedPosts)
		} catch (e: Exception) {
			Log.e("PostRepository", "Error getting posts", e)
			Resource.Error(e.localizedMessage ?: "Greška prilikom dobijanja objava")
		}
	}

}