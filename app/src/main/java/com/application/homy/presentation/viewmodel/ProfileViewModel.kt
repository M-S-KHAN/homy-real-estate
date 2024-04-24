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
class ProfileViewModel @Inject constructor(
    private val apiService: ApiService,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _loginState = MutableStateFlow<Boolean?>(null)

    fun logout() {
        sessionManager.logout()
        _loginState.value = false

    }

    fun checkIfLoggedIn() {
        _loginState.value = sessionManager.isLoggedIn()
        if (_loginState.value == true) {
            // Navigate to home screen or change UI to logged in state
            // Navigation code goes here
            println("User is logged in")
        }
    }
}
