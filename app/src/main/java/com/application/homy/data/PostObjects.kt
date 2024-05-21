package com.application.homy.data


data class LoginRequest(val email: String, val password: String)
data class BidRequest(val user_id: Int, val property_id: Int , val bid_amount: Double, val message: String?)

data class RegisterRequest(val username: String, val email: String, val password: String)


data class LoginResult(
    val success: Boolean,
    val message: String,
    val user: User?
)

data class BidResult(
    val success: Boolean,
    val message: String,
)

data class RegisterResult(
    val success: Boolean,
    val message: String,
    val user: User?
)