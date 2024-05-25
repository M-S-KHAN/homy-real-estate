package com.application.homy.data

data class PropertyDetail(
    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val address: String,
    val lat: Double,
    val lng: Double,
    val owner: User,
    val images: List<Image>,
    val created_at: String,
    val is_favorite: Boolean,
    val has_bidded: Boolean
)

data class Image(
    val id: Int,
    val image_url: String,
)