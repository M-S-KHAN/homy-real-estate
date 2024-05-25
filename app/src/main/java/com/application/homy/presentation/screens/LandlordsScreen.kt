package com.application.homy.presentation.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.application.homy.data.UserListResponse
import com.application.homy.presentation.elements.CustomScaffold
import com.application.homy.presentation.elements.UserTile
import com.application.homy.presentation.viewmodel.AuthViewModel
import com.application.homy.presentation.viewmodel.LandlordsViewModel
import com.application.homy.service.ApiResponse
import kotlinx.coroutines.CoroutineScope

@Composable
fun LandlordsScreen(navController: NavHostController, snackbarHostState: SnackbarHostState) {

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    CustomScaffold(title = "Landlords",
        snackbarHostState = snackbarHostState,
        floatingActionButton = {
            // Add a floating action button to add a new landlord
            FloatingActionButton(
                onClick = {
                    navController.navigate("addUser")
                },
                modifier = Modifier
                    .width(160.dp)
                    .padding(16.dp, 0.dp, 5.dp, 80.dp),
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.background,
                elevation = FloatingActionButtonDefaults.elevation(20.dp),
                shape = RoundedCornerShape(50),
            ) {
                Text("Add User", fontSize = 16.sp)
            }
        },
        body = { paddingValues ->
            LandlordsScreenContent(paddingValues, navController, snackbarHostState, scope)
        })
}

@Composable
fun LandlordsScreenContent(
    paddingValues: PaddingValues,
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope
) {
    val viewModel: LandlordsViewModel = hiltViewModel()
    val authViewModel: AuthViewModel = hiltViewModel()
    val landlordsState by viewModel.landlordsState.collectAsState()

    // Fetch landlords when the screen is first displayed
    LaunchedEffect(Unit) {
        viewModel.fetchLandlords(1, snackbarHostState)
    }

    LazyColumn(
        modifier = Modifier.padding(paddingValues), contentPadding = PaddingValues(8.dp)
    ) {
        when (landlordsState) {
            is ApiResponse.Success -> {
                val landlords = (landlordsState as ApiResponse.Success<UserListResponse>).data.users
                items(landlords) { landlord ->
                    UserTile(user = landlord, onDelete = {
                        viewModel.deleteLandlord(
                            landlord.id,
                            authViewModel.getUserId()!!.toInt(),
                            scope,
                            snackbarHostState
                        )
                    }, onEdit = {
                        navController.navigate("editLandlord/${landlord.id}")
                    })
                }
            }

            is ApiResponse.Error -> {
                item {
                    Text("Error: ${(landlordsState as ApiResponse.Error).message}")
                }
            }

            else -> {
                item {
                    Text("Loading landlords...")
                }
            }
        }
    }
}
