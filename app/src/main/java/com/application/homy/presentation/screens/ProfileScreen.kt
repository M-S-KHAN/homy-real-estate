package com.application.homy.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.application.homy.presentation.elements.CustomScaffold
import com.application.homy.presentation.viewmodel.AuthViewModel

@Composable
fun ProfileScreen(navController: NavController) {
    CustomScaffold(title = "Profile", body = { paddingValues ->
        ProfileScreenContent(paddingValues, navController)
    })
}

@Composable
fun ProfileScreenContent(paddingValues: PaddingValues, navController: NavController) {
    val viewModel: AuthViewModel = hiltViewModel()
    val menuItems = listOf("Logout")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .height(LocalConfiguration.current.screenHeightDp.dp),
        ) {
            items(menuItems) { item ->
                ProfileMenuItem(item, onClick = {
                    // Print nav graph
                    when (item) {
                        "Logout" -> {
                            viewModel.setShouldLogOut()
                            navController.navigate("login") {
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        }

//                        "Your Bids" -> navController.navigate("bids")
//                        "Profile" -> navController.navigate("editProfile")
//                        "About" -> navController.navigate("about")
                    }
                })
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun ProfileMenuItem(text: String, onClick: () -> Unit) {
    val backgroundColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(backgroundColor, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = when (text) {
//                "Your Bids" -> Icons.Filled.List
//                "Profile" -> Icons.Filled.AccountCircle
//                "About" -> Icons.Filled.Info
                "Logout" -> Icons.Filled.ExitToApp
                else -> Icons.Filled.Info
            },
            contentDescription = "Icon for $text",
            tint = MaterialTheme.colorScheme.tertiary
        )
    }
}
