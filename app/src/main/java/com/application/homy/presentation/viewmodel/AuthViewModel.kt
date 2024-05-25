package com.application.homy.presentation.viewmodel

import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.homy.data.LoginResult
import com.application.homy.data.RegisterResult
import com.application.homy.service.ApiRepository
import com.application.homy.service.ApiResponse
import com.application.homy.service.SessionManager
import com.application.homy.service.SnackbarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    private val sessionManager: SessionManager,
    private val snackbarManager: SnackbarManager
) : ViewModel() {

    private val _loginState = MutableStateFlow<ApiResponse<LoginResult>?>(null)
    val loginState: StateFlow<ApiResponse<LoginResult>?> = _loginState.asStateFlow()
    private val _isLoggingIn = MutableStateFlow(false)
    val isLoggingIn = _isLoggingIn.asStateFlow()

    private val _registerState = MutableStateFlow<ApiResponse<RegisterResult>?>(null)
    val registerState: StateFlow<ApiResponse<RegisterResult>?> = _registerState.asStateFlow()
    private val _isRegistering = MutableStateFlow(false)
    val isRegistering = _isRegistering.asStateFlow()


    fun login(email: String, password: String, snackbarHostState: SnackbarHostState) {
        _isLoggingIn.value = true
        viewModelScope.launch {
            apiRepository.login(email, password).collect { response ->
                when (response) {
                    is ApiResponse.Success<*> -> {
                        sessionManager.saveUserInfo(
                            (response.data as LoginResult).user!!.id.toString(),
                            response.data.user!!.role
                        )
                    }

                    is ApiResponse.Error -> {
                        snackbarManager.showError(
                            snackbarHostState,
                            response.message,
                        )
                        System.out.println(response.message)
                    }

                    else -> {
                        Log.d("AuthViewModel", "Logging in...")
                    }
                }
                _loginState.value = response

            }
            _isLoggingIn.value = false
        }
    }

    fun register(
        username: String, email: String, password: String, snackbarHostState: SnackbarHostState
    ) {
        _isRegistering.value = true
        viewModelScope.launch {
            apiRepository.register(username, email, password).collect { response ->
                when (response) {
                    is ApiResponse.Success<*> -> {
                        snackbarManager.showInfo(snackbarHostState, "Registration Successful")
                        sessionManager.saveUserInfo(
                            (response.data as LoginResult).user!!.id.toString(),
                            (response.data as LoginResult).user!!.role.toString()
                        )
                    }

                    is ApiResponse.Error -> {
                        snackbarManager.showError(snackbarHostState, response.message)
                    }

                    else -> {
                        Log.d("AuthViewModel", "Registering...")
                    }
                }
                _registerState.value = response
            }
            _isRegistering.value = false
        }
    }

    fun checkIfLoggedIn(): Boolean {
        return sessionManager.isLoggedIn()
    }

    fun getUserId(): String? {
        return sessionManager.fetchUserId()
    }

    fun getRole(): String? {
        return sessionManager.fetchUserRole()
    }

    fun logout() {
        sessionManager.logout()
    }

    fun shouldLogOut(): Boolean {
        return sessionManager.fetchShouldLogOut()
    }

    fun setShouldLogOut() {
        sessionManager.setShouldLogOut()
    }

    fun clearShouldLogOut() {
        sessionManager.clearShouldLogOut()
    }
}
