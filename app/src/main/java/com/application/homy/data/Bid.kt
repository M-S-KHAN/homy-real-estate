package com.application.homy.data

data class Bid(
    val id: Int,
    val bid_amount: String,
    val message: String,
    val created_at: String,
    val by: BidUser,
    val property: BidProperty
)

data class BidUser(
    val user_id: Int,
    val username: String,
    val email: String
)

data class BidProperty(
    val property_id: Int,
    val title: String,
    val description: String
)
