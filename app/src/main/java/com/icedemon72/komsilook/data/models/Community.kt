package com.icedemon72.komsilook.data.models

data class Community(
	var uid: String? = null,
	val name: String,
	val description: String,
	val location: String,
	val isPrivate: Boolean,
	val createdBy: String,
	var createdAt: Any? = null
)