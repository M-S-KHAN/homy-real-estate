package com.application.homy.presentation.viewmodel

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.application.homy.data.CreateUserRequest
import com.application.homy.data.MessageResponse
import com.application.homy.data.UserListResponse
import com.application.homy.service.ApiRepository
import com.application.homy.service.ApiResponse
import com.application.homy.service.SnackbarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandlordsViewModel @Inject constructor(
    private val apiRepository: ApiRepository, private val snackbarManager: SnackbarManager
) : ViewModel() {

    private val _landlordsState = MutableStateFlow<ApiResponse<UserListResponse>?>(null)
    val landlordsState: StateFlow<ApiResponse<UserListResponse>?> = _landlordsState.asStateFlow()

    private val _isAddingUser = MutableStateFlow(false)
    val isAddingUser = _isAddingUser.asStateFlow()


    fun fetchLandlords(userId: Int, snackbarHostState: SnackbarHostState) {
        viewModelScope.launch {
            apiRepository.getLandlords(userId).collect { response ->
                _landlordsState.value = response
                when (response) {
                    is ApiResponse.Success -> {
                        println("Landlords fetched successfully")
                    }

                    is ApiResponse.Error -> snackbarManager.showError(
                        snackbarHostState, response.message
                    )

                    else -> {
                        println("Fetching properties...")
                    }
                }
            }
        }
    }

    fun deleteLandlord(
        userId: Int, adminId: Int, scope: CoroutineScope, snackbarHostState: SnackbarHostState
    ) {
        viewModelScope.launch {
            apiRepository.deleteUser(userId, adminId).collect { response ->
                when (response) {
                    is ApiResponse.Success<MessageResponse> -> {
                        scope.launch {
                            snackbarHostState.showSnackbar("Landlord deleted successfully")
                        }
                        println(response.data.message)
                        fetchLandlords(adminId, snackbarHostState)
                    }

                    is ApiResponse.Error -> snackbarManager.showError(
                        snackbarHostState, response.message
                    )

                    else -> {
                        println("Deleting landlord...")
                    }
                }
            }
        }
    }

    fun addLandlord(user: CreateUserRequest, snackbarHostState: SnackbarHostState, navController: NavController) {
        viewModelScope.launch {
            _isAddingUser.value = true
            apiRepository.createUser(user).collect { response ->
                when (response) {
                    is ApiResponse.Success<MessageResponse> -> {
                        snackbarManager.showInfo(
                            snackbarHostState, response.data.message
                        )
                        fetchLandlords(user.admin_id, snackbarHostState)
                        navController.popBackStack()
                    }

                    is ApiResponse.Error -> snackbarManager.showError(
                        snackbarHostState, response.message
                    )

                    else -> {
                        println("Adding user...")
                    }
                }
            }
        }
        _isAddingUser.value = false
    }
}
