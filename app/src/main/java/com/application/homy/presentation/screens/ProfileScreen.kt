package com.application.homy.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.application.homy.presentation.elements.CustomScaffold

@Composable
fun ProfileScreen(navController: NavController) {
    CustomScaffold(title = "Profile", body = { paddingValues ->
        ProfileScreenContent(paddingValues)
    })
}

@Composable
fun ProfileScreenContent(it: PaddingValues) {
    Column(
        modifier = Modifier.padding(top = it.calculateTopPadding()),
    ) {
        Text(
            text = "Browse Page",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Normal
        )
    }
}
