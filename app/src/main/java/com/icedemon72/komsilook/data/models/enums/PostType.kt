package com.icedemon72.komsilook.data.models.enums

enum class PostType(val displayName: String) {
	OBAVESTENJE("Obaveštenje"),
	PREDLOG("Predlog"),
	PROBLEM("Problem");

	override fun toString(): String = displayName
}