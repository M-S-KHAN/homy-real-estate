package com.application.homy.data

data class Bid (
    val id: Int,
    val property_id: Int,
    val user_id: Int,
    val amount: Double,
    val message: String,
    val created_at: String
)