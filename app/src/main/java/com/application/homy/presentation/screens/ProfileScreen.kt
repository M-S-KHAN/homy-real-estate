package com.application.homy.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
fun ProfileScreenContent(it: PaddingValues, navController: NavController) {

    val viewModel: AuthViewModel = hiltViewModel()

    Column(
        modifier = Modifier.padding(top = it.calculateTopPadding()),
    ) {
        // Logout button
        TextButton(onClick = {
            // Logout
            viewModel.logout()

            // Remove current route and navigate to login screen
//            try {
//                navController.navigate("login") {
//                    // Replace current destination with new one
//                    popUpTo("main") {
//                        inclusive = true
//                    }
//                }
//            } catch (e: Exception) {
//                // Do nothing
//            }


        }) {
            Text(
                text = "Logout",
                color = Color.White,
            )
        }
    }
}
