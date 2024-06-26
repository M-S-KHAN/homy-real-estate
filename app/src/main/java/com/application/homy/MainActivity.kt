package com.application.homy

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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.application.homy.presentation.screens.AddPropertyScreen
import com.application.homy.presentation.screens.AddUserScreen
import com.application.homy.presentation.screens.BidsScreen
import com.application.homy.presentation.screens.BottomNavigationBar
import com.application.homy.presentation.screens.BrowseScreen
import com.application.homy.presentation.screens.FavouritesScreen
import com.application.homy.presentation.screens.LandlordsScreen
import com.application.homy.presentation.screens.LoginScreen
import com.application.homy.presentation.screens.ProfileScreen
import com.application.homy.presentation.screens.PropertiesScreen
import com.application.homy.presentation.screens.PropertyDetailsScreen
import com.application.homy.presentation.screens.RegistrationScreen
import com.application.homy.presentation.viewmodel.AuthViewModel
import com.application.homy.presentation.viewmodel.BrowseViewModel
import com.application.homy.ui.theme.HomyTheme
import dagger.hilt.android.AndroidEntryPoint

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
            "browse", "landlords", "favourites", "profile", "properties", "bids"
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun AppUI() {
        val navController = rememberNavController()  // Create and remember the NavController
        val snackbarHostState = remember { SnackbarHostState() }

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
                        navController, innerPadding, snackbarHostState
                    ) // Pass NavController to the AppNavigation
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun AppNavigation(
        navController: NavHostController,
        innerPadding: PaddingValues,
        snackbarHostState: SnackbarHostState
    ) {

        // Get the view model using hilt
        val auth: AuthViewModel = hiltViewModel()
        val startDestination = if (auth.checkIfLoggedIn()) "main" else "login"
        println("check logged in called from home" + auth.checkIfLoggedIn())
        val mainStart = if (auth.getRole() == "admin") "landlords" else "browse"

        val logoPainter = painterResource(id = R.drawable.logo_full)

        NavHost(navController = navController, startDestination = startDestination) {
            composable("login") { LoginScreen(logoPainter, navController, snackbarHostState) }
            composable("register") {
                RegistrationScreen(
                    logoPainter, navController, snackbarHostState
                )
            }
            navigation(startDestination = mainStart, route = "main") {
                composable("browse") { BrowseScreen(navController, snackbarHostState) }
                composable("landlords") { LandlordsScreen(navController, snackbarHostState) }
                composable("properties") { PropertiesScreen(navController, snackbarHostState) }
                composable(
                    "propertyDetails/{propertyId}",
                    arguments = listOf(navArgument("propertyId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val propertyId = backStackEntry.arguments?.getInt("propertyId")
                    PropertyDetailsScreen(
                        propertyId!!, auth.getUserId()!!.toInt(), navController, snackbarHostState
                    )
                }
                composable("favourites") { FavouritesScreen() }
                composable("addUser") { AddUserScreen(navController, snackbarHostState) }
                composable("addProperty") { AddPropertyScreen(navController, snackbarHostState) }
                composable("profile") { ProfileScreen(navController) }
                composable("bids") { BidsScreen(navController, snackbarHostState)}
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
