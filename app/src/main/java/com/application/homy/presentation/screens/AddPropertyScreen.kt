package com.application.homy.presentation.screens


import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.application.homy.data.AddPropertyRequest
import com.application.homy.presentation.elements.CustomScaffold
import com.application.homy.presentation.elements.GalleryImagePicker
import com.application.homy.presentation.viewmodel.AuthViewModel
import com.application.homy.presentation.viewmodel.BrowseViewModel
import com.application.homy.ui.theme.LogoYellow

@Composable
fun AddPropertyScreen(navController: NavController, snackbarHostState: SnackbarHostState) {
    val viewModel: BrowseViewModel = hiltViewModel()
    val authViewModel: AuthViewModel = hiltViewModel()

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var lat by remember { mutableStateOf("") }
    var lng by remember { mutableStateOf("") }
    var images by remember { mutableStateOf(listOf<Uri>()) }

    val isAddingProperty by viewModel.isAddingProperty.collectAsState()

    val isTitleValid = title.isNotEmpty()
    val isDescriptionValid = description.isNotEmpty()
    val isAddressValid = address.isNotEmpty()
    val isPriceValid = price.isNotEmpty()
    val isLatValid = lat.isNotEmpty()
    val isLngValid = lng.isNotEmpty()

    CustomScaffold(title = "Add New Property", body = { paddingValues ->
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxSize()
                            .padding(16.dp, 90.dp, 16.dp, 30.dp)
                    ) {
                        Column {
                            OutlinedTextField(
                                value = title,
                                onValueChange = { title = it },
                                label = { Text("Title") },
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    focusedBorderColor = MaterialTheme.colorScheme.secondary,
                                    unfocusedBorderColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    cursorColor = Color.White,
                                    unfocusedLabelColor = Color.White,
                                    focusedLabelColor = MaterialTheme.colorScheme.secondary,
                                )
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            OutlinedTextField(
                                value = description,
                                onValueChange = { description = it },
                                label = { Text("Description") },
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    focusedBorderColor = MaterialTheme.colorScheme.secondary,
                                    unfocusedBorderColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    cursorColor = Color.White,
                                    unfocusedLabelColor = Color.White,
                                    focusedLabelColor = MaterialTheme.colorScheme.secondary,
                                )
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            OutlinedTextField(
                                value = address,
                                onValueChange = { address = it },
                                label = { Text("Address") },
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    focusedBorderColor = MaterialTheme.colorScheme.secondary,
                                    unfocusedBorderColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    cursorColor = Color.White,
                                    unfocusedLabelColor = Color.White,
                                    focusedLabelColor = MaterialTheme.colorScheme.secondary,
                                )
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            OutlinedTextField(
                                value = price,
                                onValueChange = { price = it },
                                label = { Text("Price") },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions.Default.merge(
                                    other = KeyboardOptions(
                                        keyboardType = KeyboardType.Number
                                    )
                                ),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    focusedBorderColor = MaterialTheme.colorScheme.secondary,
                                    unfocusedBorderColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    cursorColor = Color.White,
                                    unfocusedLabelColor = Color.White,
                                    focusedLabelColor = MaterialTheme.colorScheme.secondary,
                                )
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            OutlinedTextField(
                                value = lat,
                                onValueChange = { lat = it },
                                label = { Text("Latitude") },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions.Default.merge(
                                    other = KeyboardOptions(
                                        keyboardType = KeyboardType.Number
                                    )
                                ),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    focusedBorderColor = MaterialTheme.colorScheme.secondary,
                                    unfocusedBorderColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    cursorColor = Color.White,
                                    unfocusedLabelColor = Color.White,
                                    focusedLabelColor = MaterialTheme.colorScheme.secondary,
                                )
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            OutlinedTextField(
                                value = lng,
                                onValueChange = { lng = it },
                                label = { Text("Longitude") },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions.Default.merge(
                                    other = KeyboardOptions(
                                        keyboardType = KeyboardType.Number
                                    )
                                ),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    focusedBorderColor = MaterialTheme.colorScheme.secondary,
                                    unfocusedBorderColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    cursorColor = Color.White,
                                    unfocusedLabelColor = Color.White,
                                    focusedLabelColor = MaterialTheme.colorScheme.secondary,
                                )
                            )
                            Spacer(modifier = Modifier.height(5.dp))

                            Spacer(modifier = Modifier.height(24.dp))
                            GalleryImagePicker(onImagesPicked = { imgs ->
                                // Handle the selected images URIs
                                images = imgs
                            })
                        }

                        Button(
                            onClick = {
                                val addPropertyRequest = AddPropertyRequest(
                                    title = title,
                                    description = description,
                                    address = address,
                                    price = price.toInt(),
                                    lat = lat.toDouble(),
                                    lng = lng.toDouble(),
                                    owner_id = authViewModel.getUserId()!!.toInt()
                                )
                                viewModel.addProperty(
                                    addPropertyRequest, images, snackbarHostState, navController
                                )

                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = Color.Black,
                                disabledContainerColor = Color.Gray,
                            ),
                            enabled = !isAddingProperty && isTitleValid && isDescriptionValid && isAddressValid && isPriceValid && isLatValid && isLngValid,

                            modifier = Modifier
                                .height(50.dp)
                                .width(270.dp)
                                .background(LogoYellow, CircleShape)
                        ) {
                            if (isAddingProperty) {
                                Text(
                                    "Adding Property...",
                                    fontSize = 18.sp,
                                    color = MaterialTheme.colorScheme.background
                                )
                            } else {
                                Text(
                                    "Add Property",
                                    fontSize = 18.sp,
                                    color = MaterialTheme.colorScheme.background
                                )
                            }
                        }
                    }
                }
            }
        }
    })
}