package com.application.homy.presentation.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.application.homy.data.DeletePropertyRequest
import com.application.homy.data.Property
import com.application.homy.presentation.elements.AdminPropertyCard
import com.application.homy.presentation.elements.CustomScaffold
import com.application.homy.presentation.elements.PropertyCard
import com.application.homy.presentation.viewmodel.AuthViewModel
import com.application.homy.presentation.viewmodel.BrowseViewModel
import com.application.homy.service.ApiResponse

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PropertiesScreen(navController: NavHostController, snackbarHostState: SnackbarHostState) {
    CustomScaffold(title = "Properties", floatingActionButton = {
        // Add a floating action button to add a new landlord
        FloatingActionButton(
            onClick = {
                navController.navigate("addProperty")
            },
            modifier = Modifier
                .width(160.dp)
                .padding(16.dp, 0.dp, 5.dp, 80.dp),
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.background,
            elevation = FloatingActionButtonDefaults.elevation(20.dp),
            shape = RoundedCornerShape(50),
        ) {
            Text("Add Property", fontSize = 16.sp)
        }
    }, body = { paddingValues ->
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

                if (properties.isEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                "No properties found.",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                } else {
                    items(properties.size) { index ->
                        AdminPropertyCard(property = properties[index], onEdit = {
                            // Take to details screen
                            navController.navigate("propertyDetails/${properties[index].id}")
                        }, onDelete = {
                            val deletePropertyRequest = DeletePropertyRequest(properties[index].id, authModel.getUserId()!!.toInt())
                            viewModel.deleteProperty(deletePropertyRequest, snackbarHostState)
                        })
                    }
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
