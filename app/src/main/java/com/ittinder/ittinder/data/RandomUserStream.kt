package com.ittinder.ittinder.data

import com.squareup.moshi.Json

data class RandomUserStream(

	@Json(name="RandomUserStream")
	val randomUserStream: List<RandomUserStreamItem?>? = null
)

data class RandomUserStreamItem(

	@Json(name="firstName")
	val firstName: String? = null,

	@Json(name="gender")
	val gender: String? = null,

	@Json(name="surname")
	val surname: String? = null,

	@Json(name="description")
	val description: String? = null,

	@Json(name="dateOfBirth")
	val dateOfBirth: String? = null,

	@Json(name="id")
	val id: Int? = null,

	@Json(name="interestedInGender")
	val interestedInGender: String? = null,

	@Json(name="email")
	val email: String? = null
)
