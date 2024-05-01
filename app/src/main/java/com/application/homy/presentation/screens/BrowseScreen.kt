package com.application.homy.presentation.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.application.homy.presentation.elements.CustomScaffold
import com.application.homy.presentation.elements.PropertyCard

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BrowseScreen() {
    CustomScaffold(title = "Browse Properties", body = { paddingValues ->
        BrowseScreenContent(paddingValues)
    })
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BrowseScreenContent(paddingValues: PaddingValues) {

    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        contentPadding = PaddingValues(20.dp),
    ) {
        items(10) {
            PropertyCard()
        }
    }
}
