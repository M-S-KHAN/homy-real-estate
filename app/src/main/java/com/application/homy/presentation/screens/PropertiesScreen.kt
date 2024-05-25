package com.application.homy.presentation.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.application.homy.data.Property
import com.application.homy.presentation.elements.CustomScaffold
import com.application.homy.presentation.elements.PropertyCard
import com.application.homy.presentation.viewmodel.AuthViewModel
import com.application.homy.presentation.viewmodel.BrowseViewModel
import com.application.homy.service.ApiResponse

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PropertiesScreen(navController: NavHostController, snackbarHostState: SnackbarHostState) {
    CustomScaffold(title = "Browse Properties", body = { paddingValues ->
        PropertiesScreenContent(paddingValues, navController, snackbarHostState)
    })
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PropertiesScreenContent(
    paddingValues: PaddingValues,
    navController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    val viewModel: BrowseViewModel = hiltViewModel()
    val authModel: AuthViewModel = hiltViewModel()
    val propertiesState by viewModel.propertiesState.collectAsState()

    // Fetch properties when the screen is first displayed
    LaunchedEffect(Unit) {
        viewModel.fetchProperties(authModel.getUserId()!!.toInt(), snackbarHostState)
    }

    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        contentPadding = PaddingValues(8.dp)
    ) {
        when (propertiesState) {
            is ApiResponse.Success -> {
                val properties = (propertiesState as ApiResponse.Success<List<Property>>).data
                items(properties.size) { index ->
                    PropertyCard(property = properties[index], onTap = {
                        // Take to details screen
                        navController.navigate("propertyDetails/${properties[index].id}")
                    })
                }
            }

            is ApiResponse.Error -> {
                item {
                    Text("Error: ${(propertiesState as ApiResponse.Error).message}")
                }
            }

            else -> {
                item {
                    Text("Loading properties...")
                }
            }
        }
    }
}
