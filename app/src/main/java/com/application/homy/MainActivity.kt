package com.application.homy

import LoginScreenWithScaffold
import RegistrationScreenWithScaffold
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.application.homy.presentation.screens.HomeScreen
import com.application.homy.ui.theme.HomyTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }

    @Composable
    fun AppNavigation() {
        val navController = rememberNavController()
        val logoPainter = painterResource(id = R.drawable.logo_full)

        NavHost(navController = navController, startDestination = "login") {
            composable("login") {
                // Using Hilt to inject the LoginViewModel
                LoginScreenWithScaffold(logoPainter, navController)
            }
            composable("register") {
                // Using Hilt to inject the RegisterViewModel
                RegistrationScreenWithScaffold(logoPainter, navController)
            }
            composable("home") {
                HomeScreen()
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        HomyTheme {
            AppNavigation() // Previewing the navigation setup
        }
    }
}
