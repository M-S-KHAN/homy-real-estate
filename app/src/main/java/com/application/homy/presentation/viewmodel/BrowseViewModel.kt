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

    private val _propertyState = MutableStateFlow<ApiResponse<Property>?>(null)
    val propertyState: StateFlow<ApiResponse<Property>?> = _propertyState.asStateFlow()

    private val _isCreatingBid = MutableStateFlow(false)
    val isCreatingBid = _isCreatingBid.asStateFlow()

    private val _hasBidded = MutableStateFlow(true)
    val hasBidded = _hasBidded.asStateFlow()

    fun setCreatingBid(isCreatingBid: Boolean) {
        _isCreatingBid.value = isCreatingBid
    }

    fun setHasBidded(hasBidded: Boolean) {
        _hasBidded.value = hasBidded
    }

    fun fetchProperties(userId: Int, snackbarHostState: SnackbarHostState) {
        viewModelScope.launch {
            apiRepository.getProperties(userId).collect { response ->
                _propertiesState.value = response
                when (response) {
                    is ApiResponse.Success -> {
                        println("Properties fetched successfully")
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

    fun getPropertyById(propertyId: Int, userId: Int) {
        // Fetch the property details from repository or cache
        viewModelScope.launch {
            apiRepository.getProperty(userId, propertyId).collect { response ->
                _propertyState.value = response
                when (response) {
                    is ApiResponse.Success -> {
                        println("Property fetched successfully")
                    }

                    is ApiResponse.Error -> {
                        println("Property fetch failed")
                    }

                    else -> {
                        println("Fetching property...")
                    }
                }
            }
        }
    }

    fun placeBid(
        propertyId: Int,
        userId: Int,
        bidAmount: String,
        message: String,
        snackbarHostState: SnackbarHostState
    ) {
        // Place a bid on a property
        viewModelScope.launch {
            apiRepository.createBid(userId, propertyId, bidAmount.toDouble(), message)
                .collect { response ->
                    run {
                        _isCreatingBid.value = false
                        when (response) {
                            is ApiResponse.Success -> {
                                println("Bid placed successfully")
                                _hasBidded.value = true
                                snackbarManager.showInfo(
                                    snackbarHostState, "Bid placed successfully"
                                )
                            }

                            is ApiResponse.Error -> {
                                println("Failed to place bid")
                            }

                            else -> {
                                println("Placing bid...")
                            }
                        }
                    }

                }
        }
    }

}
