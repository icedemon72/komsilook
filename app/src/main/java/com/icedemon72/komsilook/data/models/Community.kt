package com.icedemon72.komsilook.data.models

import com.google.firebase.firestore.PropertyName

data class Community(
	var id: String? = null,
	val name: String = "",
	val description: String = "",
	val location: String = "",
	val code: String = "",

	@get:PropertyName("private")
	@set:PropertyName("private")
	var isPrivate: Boolean = false,

	val createdBy: String = "",
	var createdAt: Any? = null
)