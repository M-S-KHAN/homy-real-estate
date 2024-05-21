package com.application.homy.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.application.homy.R
import com.application.homy.data.Property
import com.application.homy.presentation.elements.BidDialog
import com.application.homy.presentation.viewmodel.BrowseViewModel
import com.application.homy.service.ApiResponse
import com.google.accompanist.pager.HorizontalPager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertyDetailsScreen(
    propertyId: Int, userId: Int, navController: NavController, snackbarHostState: SnackbarHostState
) {
    val viewModel: BrowseViewModel = hiltViewModel()
    val propertyState by viewModel.propertyState.collectAsState()

    var showBidDialog by remember { mutableStateOf(false) }
    var bidAmount by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    val isCreatingBid by viewModel.isCreatingBid.collectAsState()
    val hasBidded by viewModel.hasBidded.collectAsState()


    if (showBidDialog) {
        BidDialog(onDismissRequest = { showBidDialog = false; viewModel.setCreatingBid(false); },
            bidAmount = bidAmount,
            onBidAmountChange = { bidAmount = it },
            message = message,
            onMessageChange = { message = it },
            onConfirm = {
                viewModel.placeBid(propertyId, userId, bidAmount, message, snackbarHostState)
                showBidDialog = false
            })
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }, topBar = {
        TopAppBar(title = {
            Text(
                "Property Details",
                color = MaterialTheme.colorScheme.background,
                fontWeight = FontWeight.W600,
                fontSize = 20.sp
            )
        }, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.background
                )
            }
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            titleContentColor = MaterialTheme.colorScheme.background
        )
        )
    }, containerColor = MaterialTheme.colorScheme.background, floatingActionButton = {
        FloatingActionButton(
            onClick = {
                if (hasBidded) {
                    null;
                } else {
                    showBidDialog = true; viewModel.setCreatingBid(true)
                }
            },
            modifier = Modifier
                .width(200.dp)
                .padding(16.dp),
            containerColor = if (hasBidded) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.tertiary,
            contentColor = if (hasBidded) Color.White else MaterialTheme.colorScheme.background,
            elevation = FloatingActionButtonDefaults.elevation(20.dp),
            shape = RoundedCornerShape(50),
        ) {
            if (hasBidded) {
                Text("Bid Placed", fontSize = 16.sp)
            } else {
                if (isCreatingBid) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.background,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text("Place Bid", fontSize = 16.sp)
                }
            }
        }
    }) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (val state = propertyState) {
                is ApiResponse.Success -> {
                    val property = state.data
                    viewModel.setHasBidded(property.has_bidded)
                    PropertyDetailsContent(property, Modifier.fillMaxSize())
                }

                is ApiResponse.Error -> Text(
                    "Error: ${state.message}",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )

                is ApiResponse.Loading -> CircularProgressIndicator(
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )

                null -> Text("No data", modifier = Modifier.align(Alignment.Center))
            }
        }
    }

    // Fetch property details on screen open
    LaunchedEffect(propertyId, userId) {
        viewModel.getPropertyById(propertyId, userId)
    }
}

@Composable
fun PropertyDetailsContent(property: Property, modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Top,
    ) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            ImageSlider(images = property.images)

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    property.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )
                Text(
                    "$${property.price}",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.tertiary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(20.dp))
                Text(
                    property.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
                Spacer(Modifier.height(10.dp))
                Text(
                    "Address: ${property.address}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Spacer(Modifier.height(16.dp))
            }
        }

    }
}

@Composable
fun ImageSlider(images: List<String>) {
    HorizontalPager(count = images.size, modifier = Modifier.height(220.dp)) { page ->
        Image(
            painter = rememberImagePainter(images[page], builder = {
                crossfade(true)
            }),
            contentDescription = null,
            modifier = Modifier
                .height(220.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PropertyDetailsPreview() {
    PropertyDetailsContent(
        property = Property(
            id = 1,
            title = "Beautiful House",
            description = "This is a beautiful house",
            price = 100000,
            address = "123 Main St",
            lat = 0.0,
            lng = 0.0,
            owner = com.application.homy.data.User(
                id = 1,
                username = "John Doe",
                email = "",
                profile_image_url = "",
                role = "",
            ),
            images = listOf("https://example.com/image1.jpg", "https://example.com/image2.jpg"),
            created_at = "2022-01-01",
            is_favorite = false,
            has_bidded = false
        ), modifier = Modifier.fillMaxSize()
    )
}


