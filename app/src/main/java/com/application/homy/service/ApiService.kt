package com.application.homy.service

import com.application.homy.data.LoginRequest
import com.application.homy.data.LoginResult
import com.application.homy.data.Property
import com.application.homy.data.RegisterRequest
import com.application.homy.data.RegisterResult
import retrofit2.http.*
import retrofit2.Response

interface ApiService {
    @POST("auth/login.php/")
    suspend fun login(@Body request: LoginRequest): Response<LoginResult>

    @POST("auth/register.php/")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResult>

    @GET("properties/get-properties.php")
    suspend fun getProperties(@Query("user_id") userId: Int): Response<List<Property>>
}
