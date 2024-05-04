package com.application.homy.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.application.homy.R
import com.application.homy.presentation.viewmodel.AuthViewModel
import com.application.homy.service.ApiResponse
import com.application.homy.ui.theme.LogoYellow

@Composable
fun RegistrationScreen(logo: Painter, navController: NavController) {
    val viewModel: AuthViewModel = hiltViewModel()

    val snackbarHostState = remember { SnackbarHostState() }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }

    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+" // Simple email pattern for validation
    val usernamePattern = "[a-zA-Z0-9._-]+" // Simple username pattern for validation

    val isEmailValid = email.matches(emailPattern.toRegex())
    val isUsernameValid = username.matches(usernamePattern.toRegex())
    val isPasswordValid = password.length >= 8 // Assuming valid password is more than 5 characters
    val isConfirmPasswordValid = confirmPassword == password

    val registerState by viewModel.registerState.collectAsState()
    val isRegistering by viewModel.isRegistering.collectAsState()


    LaunchedEffect(registerState) {
        when (registerState) {
            is ApiResponse.Success -> {
                // Navigate on success
                navController.navigate("main") {
                    popUpTo("login") { inclusive = true }
                }
            }

            else -> {
                // Handle other states if necessary
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        // Place other Scaffold slots if necessary
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Image(
                        painter = logo,
                        contentDescription = stringResource(id = R.string.app_name),
                        modifier = Modifier.size(200.dp)
                    )

                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text(stringResource(id = R.string.username_text)) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            focusedBorderColor = MaterialTheme.colorScheme.secondary,
                            unfocusedBorderColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = Color.White,
                            unfocusedLabelColor = Color.White,
                            focusedLabelColor = MaterialTheme.colorScheme.secondary,
                        )
                    )
                    Spacer(modifier = Modifier.height(5.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text(stringResource(id = R.string.email_text)) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            focusedBorderColor = MaterialTheme.colorScheme.secondary,
                            unfocusedBorderColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = Color.White,
                            unfocusedLabelColor = Color.White,
                            focusedLabelColor = MaterialTheme.colorScheme.secondary,
                        )
                    )
                    Spacer(modifier = Modifier.height(5.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text(stringResource(id = R.string.password_text)) },
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            focusedBorderColor = MaterialTheme.colorScheme.secondary,
                            unfocusedBorderColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = Color.White,
                            unfocusedLabelColor = Color.White,
                            focusedLabelColor = MaterialTheme.colorScheme.secondary,
                        )
                    )
                    Spacer(modifier = Modifier.height(5.dp))

                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text(stringResource(id = R.string.confirm_password_text)) },
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardActions = KeyboardActions(onDone = {
                            viewModel.register(username, email, password, snackbarHostState)
                        }),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            focusedBorderColor = MaterialTheme.colorScheme.secondary,
                            unfocusedBorderColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = Color.White,
                            unfocusedLabelColor = Color.White,
                            focusedLabelColor = MaterialTheme.colorScheme.secondary,
                        )
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            viewModel.register(username, email, password, snackbarHostState)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = Color.Black
                        ),
                        enabled = !isRegistering && isEmailValid && isPasswordValid && isUsernameValid && isConfirmPasswordValid,

                        modifier = Modifier
                            .height(50.dp)
                            .width(270.dp)
                            .background(LogoYellow, CircleShape)
                    ) {
                        if (isRegistering) {
                            Text(stringResource(id = R.string.registering_text), fontSize = 18.sp)

                        } else {
                            Text(stringResource(id = R.string.register_text), fontSize = 18.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))

                    TextButton(onClick = {
                        // send back via pop
                        navController.popBackStack()
                    }) {
                        Text(
                            stringResource(id = R.string.already_a_member),
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}


