package com.application.homy.presentation.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.application.homy.presentation.viewmodel.AuthViewModel

@Composable
fun BottomNavigationBar(navController: NavHostController) {

    // Get current user role
    val auth: AuthViewModel = hiltViewModel()
    val role = auth.getRole()

    val colors = NavigationBarItemDefaults.colors(
        selectedIconColor = Color.White,
        unselectedIconColor = MaterialTheme.colorScheme.background,
        indicatorColor = MaterialTheme.colorScheme.background,
        selectedTextColor = MaterialTheme.colorScheme.background,
    )

    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.secondary, tonalElevation = 50.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        if (role == "admin") {
            NavigationBarItem(icon = {
                Icon(
                    imageVector = Icons.Rounded.AccountBox, contentDescription = null
                )
            },
                label = { Text(text = "Landlords") },
                selected = currentRoute == "landlords",
                onClick = {
                    if (currentRoute != "landlords") {
                        navController.navigate("landlords") {
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
        }
        if (role == "client") {
            NavigationBarItem(icon = {
                Icon(
                    imageVector = Icons.Rounded.Home, contentDescription = null
                )
            },
                label = { Text(text = "Browse") },
                selected = currentRoute == "browse",
                onClick = {
                    if (currentRoute != "browse") {
                        navController.navigate("browse") {
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
        }

        if (role == "admin") {
            NavigationBarItem(icon = {
                Icon(
                    imageVector = Icons.Rounded.Menu, contentDescription = null
                )
            },
                label = { Text(text = "Properties") },
                selected = currentRoute == "properties",
                onClick = {
                    if (currentRoute != "properties") {
                        navController.navigate("properties") {
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
        }
        if (role == "client") {
            NavigationBarItem(icon = {
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
        }


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
