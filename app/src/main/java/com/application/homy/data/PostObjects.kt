package com.application.homy.data


data class LoginRequest(val email: String, val password: String)
data class BidRequest(
    val user_id: Int, val property_id: Int, val bid_amount: Double, val message: String?
)

data class RegisterRequest(val username: String, val email: String, val password: String)

data class CreateUserRequest(
    val username: String,
    val email: String,
    val role: String,
    val password: String,
    val admin_id: Int
)

data class AddPropertyRequest(
    val owner_id: Int,
    val title: String,
    val description: String,
    val address: String,
    val price: Int,
    val lat: Double,
    val lng: Double
)

data class BidsResponse(
    val bids: List<Bid>
)

data class AddPropertyResponse(
    val message: String, val property: MiniProperty
)

data class MiniProperty(
    val id: Int, val title: String
)

data class DeletePropertyRequest(
    val property_id: Int, val user_id: Int
)

data class AddPropertyImageRequest(
    val property_id: Int, val image_base64: String
)

data class UserListResponse(
    val users: List<User>
)

data class MessageResponse(
    val message: String
)


data class LoginResult(
    val success: Boolean, val message: String, val user: User?
)

data class BidResult(
    val success: Boolean,
    val message: String,
)

data class RegisterResult(
    val success: Boolean, val message: String, val user: User?
)