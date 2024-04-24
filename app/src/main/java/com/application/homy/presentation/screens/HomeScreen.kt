package com.application.homy.presentation.screens

import LoginScreen
import LoginScreenWithScaffold
import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.application.homy.R
import com.application.homy.presentation.viewmodel.ProfileViewModel
import com.application.homy.presentation.viewmodel.RegisterViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen() {
    val navController = rememberNavController()

    // Handling back press
    BackHandler {
        // This block is executed on back press.
        // If the current destination is the start destination, allow the system to handle the back press.
        if (navController.currentBackStackEntry?.destination?.route == navController.graph.startDestinationRoute) {
            // This will let the system handle the back press, i.e., likely close the app
            // if there are no further back stack entries
        } else {
            navController.popBackStack(navController.graph.startDestinationRoute!!, false)
        }
    }

    Scaffold(bottomBar = { BottomNavigationBar(navController) }) {
        NavigationGraph(navController = navController)
    }
}

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


@Composable
fun NavigationGraph(navController: NavHostController) {
    // print current route
    val currentRoute = navController.currentBackStackEntry?.destination?.route
    println("Current route: $currentRoute")
    val logoPainter = painterResource(id = R.drawable.logo_full)

//    val rViewModel: ProfileViewModel =
//        viewModel { ProfileViewModel(apiService, sessionManager) }

    NavHost(navController = navController, startDestination = "browse") {
        composable("browse") { BrowseScreen() }
        composable("favourites") { FavouritesScreen() }
        composable("profile") { ProfileScreen(navController) }
        composable("login") { LoginScreenWithScaffold(logoPainter, navController) }
    }
}
