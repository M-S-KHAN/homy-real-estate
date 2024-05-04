package com.application.homy.data

data class Property(
    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val address: String,
    val lat: Double,
    val lng: Double,
    val owner: User,
    val images: List<String>,
    val created_at: String,
    val isFavorite: Boolean
)