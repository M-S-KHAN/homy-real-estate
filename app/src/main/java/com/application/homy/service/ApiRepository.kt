package com.application.homy.service

import com.application.homy.data.LoginRequest
import com.application.homy.data.LoginResult
import com.application.homy.data.Property
import com.application.homy.data.RegisterRequest
import com.application.homy.data.RegisterResult
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val message: String) : ApiResponse<Nothing>()
}

class ApiRepository @Inject constructor(
    private val apiService: ApiService,
    private val networkChecker: NetworkChecker
) {
    suspend fun login(email: String, password: String): Flow<ApiResponse<LoginResult>> = flow {
        val gson = Gson()
        if (!networkChecker.hasInternetConnection()) {
            emit(ApiResponse.Error("No internet connection available"))
            return@flow
        }
        try {
            val response = apiService.login(LoginRequest(email, password))
            if (response.isSuccessful && response.body() != null) {
                emit(ApiResponse.Success(response.body()!!))
            } else {
                emit(ApiResponse.Error("Failed to login: ${gson.fromJson(response.errorBody()?.string(), RegisterResult::class.java).message}"))
            }
        } catch (e: Exception) {
            emit(ApiResponse.Error("An error occurred: ${e.localizedMessage}"))
        }
    }

    suspend fun register(username: String, email: String, password: String): Flow<ApiResponse<RegisterResult>> = flow {
        val gson = Gson()
        if (!networkChecker.hasInternetConnection()) {
            emit(ApiResponse.Error("No internet connection available"))
            return@flow
        }
        try {
            val response = apiService.register(RegisterRequest(username, email, password))
            if (response.isSuccessful && response.body() != null) {
                emit(ApiResponse.Success(response.body()!!))
            } else {
                emit(ApiResponse.Error("Failed to register: ${gson.fromJson(response.errorBody()?.string(), RegisterResult::class.java).message}"))
            }
        } catch (e: Exception) {
            emit(ApiResponse.Error("An error occurred: ${e.localizedMessage}"))
        }
    }

    suspend fun getProperties(userId: Int): Flow<ApiResponse<List<Property>>> = flow {
        if (!networkChecker.hasInternetConnection()) {
            emit(ApiResponse.Error("No internet connection available"))
            return@flow
        }

        try {
            val response = apiService.getProperties(userId)
            if (response.isSuccessful && response.body() != null) {
                emit(ApiResponse.Success(response.body()!!))
            } else {
                emit(ApiResponse.Error("Failed to fetch properties: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(ApiResponse.Error("An error occurred: ${e.localizedMessage}"))
        }
    }
}
