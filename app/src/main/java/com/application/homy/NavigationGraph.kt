//package com.application.homy
//
//import LoginScreen
//import RegistrationScreen
//import androidx.compose.runtime.Composable
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.navigation
//import androidx.navigation.compose.rememberNavController
//import com.application.homy.presentation.screens.HomeScreen
//
//@Composable
//fun MainNavigation() {
//    val navController = rememberNavController()
//
//    NavHost(navController = navController, startDestination = "login") {
//        composable("login") {
//            LoginScreen(navController)
//        }
//        composable("register") {
//            RegistrationScreen(navController)
//        }
//        navigation(startDestination = "home", route = "home_graph") {
//            composable("home") {
//                HomeScreen(navController)
//            }
//            // Additional nested destinations within the home graph
//        }
//    }
//}
