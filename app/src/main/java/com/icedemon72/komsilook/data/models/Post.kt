package com.icedemon72.komsilook.data.models

import java.util.Date

data class Post (
	var id: String? = null,
	val title: String? = null,
	val description: String? = null,
	val type: String? = null,
	val createdBy: String? = null,
	val createdAt: Date? = null,
	val communityId: String? = null
)
