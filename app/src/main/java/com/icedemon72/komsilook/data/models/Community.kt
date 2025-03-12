package com.icedemon72.komsilook.data.models

import com.google.firebase.firestore.PropertyName

data class Community(
	var uid: String? = null,
	val name: String,
	val description: String,
	val location: String,

	@PropertyName("private")
	val isPrivate: Boolean = false,

	val createdBy: String,
	var createdAt: Any? = null
)