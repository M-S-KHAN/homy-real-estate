package com.application.homy.presentation.elements

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomScaffold(
    title: String,
    body: @Composable (PaddingValues) -> Unit,
    showTopBar: Boolean = true,
    topBarTitle: @Composable () -> Unit = {
        Text(
            title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.background,
            fontWeight = FontWeight.W600,
            fontSize = 20.sp

        )
    },
    topBarBackgroundColor: Color = MaterialTheme.colorScheme.secondary,
    floatingActionButton: @Composable (() -> Unit)? = null,
    bottomBar: @Composable (() -> Unit)? = null,
    snackbarHostState: SnackbarHostState? = null,
) {
    Scaffold(floatingActionButton = {
        if (floatingActionButton != null) {
             floatingActionButton()
        }

    }, snackbarHost = {
        snackbarHostState?.let {
            SnackbarHost(snackbarHostState)
        }
    }, topBar = {
        if (showTopBar) {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = topBarBackgroundColor,
                ),
                title = topBarTitle,
            )
        }
    }, bottomBar = {
        bottomBar?.invoke()
    }) { paddingValues ->
        body(paddingValues)
    }
}
