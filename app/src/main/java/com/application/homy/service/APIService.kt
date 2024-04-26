package com.application.homy.service

import com.application.homy.data.User
import retrofit2.http.*
import retrofit2.Response

interface ApiService {
    @POST("auth/login.php/")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("auth/register.php/")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>
}

data class LoginRequest(val email: String, val password: String)

// Data class to match the entire JSON response
data class LoginResponse(
    val message: String,
    val user: User?
)

data class RegisterRequest(val username: String, val email: String, val password: String)

data class RegisterResponse(
    val message: String,
    val user: User?
)