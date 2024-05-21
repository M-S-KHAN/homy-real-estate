package com.application.homy.service

import com.application.homy.data.BidRequest
import com.application.homy.data.BidResult
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

    @GET("properties/get-property.php")
    suspend fun getProperty(@Query("user_id") userId: Int, @Query("property_id") propertyId: Int): Response<Property>

    @POST("bids/create-bid.php")
    suspend fun createBid(@Body request: BidRequest): Response<BidResult>
}
