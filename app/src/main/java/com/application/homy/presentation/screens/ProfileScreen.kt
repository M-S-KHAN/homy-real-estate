package com.application.homy.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.application.homy.presentation.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(navController: NavController) {

    // Get the view model using hilt
    val viewModel: ProfileViewModel = hiltViewModel()

    // Add a logout button
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Profile Screen",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }

    // Add a logout button
    TextButton(onClick = {
        // Call the logout function from the view model
         viewModel.logout()
        // Navigate to the login screen
        navController.navigate("login") {
            popUpTo("login") { inclusive = true }
        }

    }) {

        Text(
            text = "Logout",
            color = Color.White
        )
    }
}
