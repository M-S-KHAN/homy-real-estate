package com.application.homy.data

data class User(
    val id: Int,
    val username: String,
    val email: String,
    val profile_image_url: String?,
    val role: String
)