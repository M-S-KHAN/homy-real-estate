package com.application.homy.presentation.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.application.homy.data.BidsResponse
import com.application.homy.presentation.elements.BidCard
import com.application.homy.presentation.elements.CustomScaffold
import com.application.homy.presentation.viewmodel.AuthViewModel
import com.application.homy.presentation.viewmodel.BrowseViewModel
import com.application.homy.service.ApiResponse

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BidsScreen(navController: NavHostController, snackbarHostState: SnackbarHostState) {
    CustomScaffold(title = "Bids", body = { paddingValues ->
        BidsScreenContent(paddingValues, navController, snackbarHostState)
    })
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BidsScreenContent(
    paddingValues: PaddingValues,
    navController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    val viewModel: BrowseViewModel = hiltViewModel()
    val bidsState by viewModel.bidsState.collectAsState()

    val authModel: AuthViewModel = hiltViewModel()

    // Fetch properties when the screen is first displayed
    LaunchedEffect(Unit) {
        viewModel.fetchBids(authModel.getUserId()!!.toInt(), snackbarHostState)
    }

    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        contentPadding = PaddingValues(8.dp)
    ) {
        when (bidsState) {
            is ApiResponse.Success -> {
                val bids = (bidsState as ApiResponse.Success<BidsResponse>).data.bids

                if (bids.isEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                "No bids found.",
                                fontSize = 20.sp,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                } else {
                    items(bids.size) { bid ->
                        BidCard(bid = bids[bid])
                    }
                }
            }

            is ApiResponse.Error -> {
                item {
                    Text("Error: ${(bidsState as ApiResponse.Error).message}")
                }
            }

            else -> {
                item {
                    Text("Loading bids...")
                }
            }
        }
    }
}

