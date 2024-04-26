package com.application.homy.presentation.screens

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavigationBar(navController: NavHostController) {

    val colors = NavigationBarItemDefaults.colors(
        selectedIconColor = Color.White,
        unselectedIconColor = MaterialTheme.colorScheme.background,
        indicatorColor = MaterialTheme.colorScheme.background,
    )

    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.secondary, tonalElevation = 50.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        println(currentRoute)

        NavigationBarItem(icon = {
            Icon(
                imageVector = Icons.Rounded.Home, contentDescription = null
            )
        }, label = { Text(text = "Browse") }, selected = currentRoute == "browse", onClick = {
            if (currentRoute != "browse") {
                navController.navigate("browse") {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }, colors = colors
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Favorite, contentDescription = null
                )
            },
            label = { Text(text = "Favourites") },
            selected = currentRoute == "favourites",
            onClick = {
                if (currentRoute != "favourites") {
                    navController.navigate("favourites") {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            colors = colors
        )
        NavigationBarItem(icon = {
            Icon(
                imageVector = Icons.Rounded.AccountCircle, contentDescription = null
            )
        }, label = { Text(text = "Profile") }, selected = currentRoute == "profile", onClick = {
            if (currentRoute != "profile") {
                navController.navigate("profile") {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }, colors = colors
        )
    }
}
