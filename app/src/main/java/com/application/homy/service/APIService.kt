package com.application.homy.service

import com.application.homy.data.User
import retrofit2.http.*
import retrofit2.Response

interface ApiService {
    @POST("auth/login.php/")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}

data class LoginRequest(val email: String, val password: String)

// Data class to match the entire JSON response
data class LoginResponse(
    val message: String,
    val user: User?
)