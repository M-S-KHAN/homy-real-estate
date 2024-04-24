package com.application.homy.presentation.viewmodel

import androidx.lifecycle.*
import com.application.homy.service.ApiService
import com.application.homy.service.LoginRequest
import com.application.homy.service.LoginResponse
import com.application.homy.service.SessionManager
import com.application.homy.service.SnackbarManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val apiService: ApiService,
    private val sessionManager: SessionManager
) : ViewModel() {


    private val _loginState = MutableStateFlow<Boolean?>(null)
    val loginState: StateFlow<Boolean?> get() = _loginState.asStateFlow()


    fun login(email: String, password: String, snackbarManager: SnackbarManager) {
        viewModelScope.launch {
            _loginState.value = null // Optional: to ensure loading state is represented
            try {
                val response = apiService.login(LoginRequest(email, password))
                if (response.isSuccessful && response.body() != null) {
                    val loginResponse = response.body()
                    val user = loginResponse?.user
                    if (user != null) {
                        sessionManager.saveUserId(user.username)
                        println(user)
                    }
                    snackbarManager.showMessage(loginResponse!!.message)
                    _loginState.value = true
                } else {
                    val errorResponse = parseError(response)
                    snackbarManager.showMessage(errorResponse.message)
                    _loginState.value = false
                }
            } catch (e: Exception) {
                println("Exception: ${e.message}")
                _loginState.value = false
            }
        }
    }

    private fun parseError(response: Response<*>): LoginResponse {
        val gson = Gson()
        val type = object : TypeToken<LoginResponse>() {}.type
        response.errorBody()?.string()?.let {
            try {
                return gson.fromJson(it, type)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return LoginResponse(message = "Unknown error occurred", user = null)
    }

    fun checkIfLoggedIn(): Boolean {
        return sessionManager.isLoggedIn()
    }
}
