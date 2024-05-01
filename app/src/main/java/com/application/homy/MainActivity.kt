package com.application.homy

import LoginScreen
import RegistrationScreen
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.application.homy.presentation.screens.BottomNavigationBar
import com.application.homy.presentation.screens.BrowseScreen
import com.application.homy.presentation.screens.FavouritesScreen
import com.application.homy.presentation.screens.ProfileScreen
import com.application.homy.presentation.viewmodel.AuthViewModel
import com.application.homy.ui.theme.HomyTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppUI()
        }
    }

    @Composable
    fun shouldShowBottomBar(navController: NavHostController): Boolean {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        return currentRoute in listOf(
            "browse", "favourites", "profile"
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun AppUI() {
        val navController = rememberNavController()  // Create and remember the NavController

        HomyTheme {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
            ) {
                Scaffold(
                    bottomBar = {
                        if (shouldShowBottomBar(navController)) {
                            BottomNavigationBar(navController)
                        }
                    },
                ) { innerPadding ->
                    AppNavigation(
                        navController, innerPadding
                    ) // Pass NavController to the AppNavigation
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun AppNavigation(navController: NavHostController, innerPadding: PaddingValues) {

        // Get the view model using hilt
        val auth: AuthViewModel = hiltViewModel()
        val startDestination = if (auth.checkIfLoggedIn()) "main" else "main"

        val logoPainter = painterResource(id = R.drawable.logo_full)

        NavHost(navController = navController, startDestination = startDestination) {
            composable("login") { LoginScreen(logoPainter, navController) }
            composable("register") { RegistrationScreen(logoPainter, navController) }
            navigation(startDestination = "browse", route = "main") {
                composable("browse") { BrowseScreen() }
                composable("favourites") { FavouritesScreen() }
                composable("profile") { ProfileScreen(navController) }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopBar() {

        TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ), title = {
            Text("Small Top App Bar")
        })
    }


    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        HomyTheme {}
    }
}
