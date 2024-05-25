package com.application.homy.presentation.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.application.homy.data.AddPropertyImageRequest
import com.application.homy.data.AddPropertyRequest
import com.application.homy.data.AddPropertyResponse
import com.application.homy.data.Bid
import com.application.homy.data.BidsResponse
import com.application.homy.data.DeletePropertyRequest
import com.application.homy.data.MessageResponse
import com.application.homy.data.Property
import com.application.homy.data.PropertyDetail
import com.application.homy.service.ApiRepository
import com.application.homy.service.ApiResponse
import com.application.homy.service.SnackbarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@HiltViewModel
class BrowseViewModel @Inject constructor(
    private val apiRepository: ApiRepository, private val snackbarManager: SnackbarManager
) : ViewModel() {

    private val _propertiesState = MutableStateFlow<ApiResponse<List<Property>>?>(null)
    val propertiesState: StateFlow<ApiResponse<List<Property>>?> = _propertiesState.asStateFlow()

    private val _propertyState = MutableStateFlow<ApiResponse<PropertyDetail>?>(null)
    val propertyState: StateFlow<ApiResponse<PropertyDetail>?> = _propertyState.asStateFlow()

    private val _bidsState = MutableStateFlow<ApiResponse<BidsResponse>?>(null)
    val bidsState: StateFlow<ApiResponse<BidsResponse>?> = _bidsState.asStateFlow()

    private val _isCreatingBid = MutableStateFlow(false)
    val isCreatingBid = _isCreatingBid.asStateFlow()

    private val _hasBidded = MutableStateFlow(true)
    val hasBidded = _hasBidded.asStateFlow()

    private val _isAddingProperty = MutableStateFlow(false)
    val isAddingProperty = _isAddingProperty.asStateFlow()

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

    fun deleteProperty(
        deletePropertyRequest: DeletePropertyRequest,
        snackbarHostState: SnackbarHostState
    ) {
        viewModelScope.launch {
            apiRepository.deleteProperty(deletePropertyRequest).collect { response ->
                when (response) {
                    is ApiResponse.Success<MessageResponse> -> {
                        snackbarManager.showInfo(
                            snackbarHostState, response.data.message
                        )
                        fetchProperties(deletePropertyRequest.user_id, snackbarHostState)
                    }

                    is ApiResponse.Error -> snackbarManager.showError(
                        snackbarHostState, response.message
                    )

                    else -> {
                        println("Deleting property...")
                    }
                }
            }
        }
    }


    fun addProperty(
        addPropertyRequest: AddPropertyRequest,
        images: List<Uri>,
        snackbarHostState: SnackbarHostState,
        navController: NavController
    ) {
        viewModelScope.launch {
            _isAddingProperty.value = true
            apiRepository.addProperty(addPropertyRequest).collect { response ->
                when (response) {
                    is ApiResponse.Success<AddPropertyResponse> -> {
                        // Show success message

                        // Now upload images one by one
                        if (images.isNotEmpty()){
                            uploadPropertyImages(
                                response.data.property.id,
                                images,
                                snackbarHostState,
                                navController,
                                navController.context,
                            )
                        } else {
                            _isAddingProperty.value = false
                            snackbarManager.showInfo(snackbarHostState, "Property added successfully")
                            navController.popBackStack()
                        }
                    }

                    is ApiResponse.Error -> {
                        // Show error message
                        snackbarManager.showError(snackbarHostState, response.message)
                    }

                    else -> {
                        println("Adding property...")
                    }
                }
            }
        }
    }

    private fun uploadPropertyImages(
        propertyId: Int,
        images: List<Uri>,
        snackbarHostState: SnackbarHostState,
        navController: NavController,
        context: Context
    ) {
        images.forEach { imageUri ->
            viewModelScope.launch {
                val base64Image =
                    withContext(Dispatchers.IO) { convertImageToBase64(context, imageUri) }
                if (base64Image != null) {
                    apiRepository.uploadPropertyImage(AddPropertyImageRequest(propertyId, base64Image)).collect { response ->
                        when (response) {
                            is ApiResponse.Success<MessageResponse> -> {
                                _isAddingProperty.value = false
                                snackbarManager.showInfo(snackbarHostState, "Property added successfully")
                                navController.popBackStack()
                            }

                            is ApiResponse.Error -> {
                                // Show error for image upload, log it, or handle it depending on the requirement
                                snackbarManager.showError(
                                    snackbarHostState,
                                    "Failed to upload image: ${response.message}"
                                )
                            }

                            else -> {
                                println("Uploading image...")
                            }
                        }
                    }
                } else {
                    snackbarManager.showError(snackbarHostState, "Failed to encode image")
                }
            }
        }
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun convertImageToBase64(context: Context, imageUri: Uri): String? {
        return try {
            val bitmap = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, imageUri)
                ImageDecoder.decodeBitmap(source)
            }

            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            val bytes = outputStream.toByteArray()
            Base64.encode(bytes)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun fetchBids(userId: Int, snackbarHostState: SnackbarHostState) {
        viewModelScope.launch {
            apiRepository.getBids(userId).collect { response ->
                _bidsState.value = response
                when (response) {
                    is ApiResponse.Success -> {
                        println("Bids fetched successfully")
                    }

                    is ApiResponse.Error -> snackbarManager.showError(
                        snackbarHostState, response.message
                    )

                    else -> {
                        println("Fetching bids...")
                    }
                }
            }
        }
    }
}
