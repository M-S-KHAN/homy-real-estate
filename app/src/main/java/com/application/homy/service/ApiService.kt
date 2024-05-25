package com.application.homy.service

import com.application.homy.data.BidRequest
import com.application.homy.data.BidResult
import com.application.homy.data.CreateUserRequest
import com.application.homy.data.LoginRequest
import com.application.homy.data.LoginResult
import com.application.homy.data.MessageResponse
import com.application.homy.data.Property
import com.application.homy.data.PropertyDetail
import com.application.homy.data.RegisterRequest
import com.application.homy.data.RegisterResult
import com.application.homy.data.User
import com.application.homy.data.UserListResponse
import retrofit2.http.*
import retrofit2.Response

interface ApiService {
    @POST("auth/login.php/")
    suspend fun login(@Body request: LoginRequest): Response<LoginResult>

    @POST("auth/register.php/")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResult>

    @GET("properties/get-properties.php")
    suspend fun getProperties(@Query("user_id") userId: Int): Response<List<Property>>

    @GET("users/get-users.php")
    suspend fun getLandlords(@Query("user_id") userId: Int, @Query("role") role: String): Response<UserListResponse>

    @GET("properties/get-property.php")
    suspend fun getProperty(@Query("user_id") userId: Int, @Query("property_id") propertyId: Int): Response<PropertyDetail>

    @POST("bids/create-bid.php")
    suspend fun createBid(@Body request: BidRequest): Response<BidResult>

    @DELETE("users/delete-user.php")
    suspend fun deleteUser(@Query("user_id") userId: Int, @Query("admin_id") adminId: Int): Response<MessageResponse>

    @POST("users/add-user.php")
    suspend fun createUser(@Body request: CreateUserRequest): Response<MessageResponse>
}
