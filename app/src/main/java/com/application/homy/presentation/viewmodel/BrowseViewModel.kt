package com.application.homy.presentation.viewmodel

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.homy.data.Property
import com.application.homy.service.ApiRepository
import com.application.homy.service.ApiResponse
import com.application.homy.service.SnackbarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BrowseViewModel @Inject constructor(
    private val apiRepository: ApiRepository, private val snackbarManager: SnackbarManager
) : ViewModel() {

    private val _propertiesState = MutableStateFlow<ApiResponse<List<Property>>?>(null)
    val propertiesState: StateFlow<ApiResponse<List<Property>>?> = _propertiesState.asStateFlow()

    fun fetchProperties(userId: Int, snackbarHostState: SnackbarHostState) {
        viewModelScope.launch {
            apiRepository.getProperties(userId).collect { response ->
                _propertiesState.value = response
                when (response) {
                    is ApiResponse.Success -> snackbarManager.showInfo(
                        snackbarHostState, "Properties fetched successfully"
                    )

                    is ApiResponse.Error -> snackbarManager.showError(
                        snackbarHostState, response.message
                    )
                }
            }
        }
    }
}
