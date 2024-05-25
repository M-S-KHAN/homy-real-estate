package com.application.homy.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.application.homy.R
import com.application.homy.data.CreateUserRequest
import com.application.homy.presentation.elements.CustomScaffold
import com.application.homy.presentation.viewmodel.AuthViewModel
import com.application.homy.presentation.viewmodel.LandlordsViewModel
import com.application.homy.ui.theme.LogoYellow

@Composable
fun AddUserScreen(navController: NavController, snackbarHostState: SnackbarHostState) {
    val viewModel: LandlordsViewModel = hiltViewModel()
    val authViewModel: AuthViewModel = hiltViewModel()

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("") }

    val isAddingUser by viewModel.isAddingUser.collectAsState()

    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+" // Simple email pattern for validation
    val isEmailValid = email.matches(emailPattern.toRegex())
    val isPasswordValid = password.length >= 8
    val isRoleValid = role == "admin" || role == "client" || role == "agent"
    val isUsernameValid = username.length >= 3 && username.isNotEmpty()

    CustomScaffold(title = "Add New User", body = { paddingValues ->
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
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
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxSize()
                            .padding(16.dp, 90.dp, 16.dp, 30.dp)
                    ) {
                        Column {
                            OutlinedTextField(
                                value = username,
                                onValueChange = { username = it },
                                label = { Text("Username") },
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
                                value = role,
                                onValueChange = { role = it },
                                label = { Text("Role") },
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
                            Spacer(modifier = Modifier.height(24.dp))
                        }
                        Button(
                            onClick = {
                                val createUserRequest = CreateUserRequest(
                                    username = username,
                                    email = email,
                                    password = password,
                                    role = role,
                                    admin_id = authViewModel.getUserId()!!.toInt()
                                )
                                viewModel.addLandlord(
                                    createUserRequest,
                                    snackbarHostState,
                                    navController
                                )
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = Color.Black,
                                disabledContainerColor = Color.Gray,
                            ),
                            enabled = !isAddingUser && isEmailValid && isPasswordValid && isRoleValid && isUsernameValid,

                            modifier = Modifier
                                .height(50.dp)
                                .width(270.dp)
                                .background(LogoYellow, CircleShape)
                        ) {
                            if (isAddingUser) {
                                Text(
                                    "Adding User...",
                                    fontSize = 18.sp,
                                    color = MaterialTheme.colorScheme.background
                                )
                            } else {
                                Text(
                                    "Add User",
                                    fontSize = 18.sp,
                                    color = MaterialTheme.colorScheme.background
                                )
                            }
                        }
                    }
                }
            }
        }
    })
}