package com.application.homy.data


data class LoginRequest(val email: String, val password: String)

data class RegisterRequest(val username: String, val email: String, val password: String)


data class LoginResult(
    val success: Boolean,
    val message: String,
    val user: User?
)

data class RegisterResult(
    val success: Boolean,
    val message: String,
    val user: User?
)