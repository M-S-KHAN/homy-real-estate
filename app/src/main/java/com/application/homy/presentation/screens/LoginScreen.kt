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
//import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.application.homy.R
import com.application.homy.presentation.viewmodel.LoginViewModel
import com.application.homy.service.SnackbarManager
import com.application.homy.ui.theme.LogoYellow

import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun LoginScreenWithScaffold(logo: Painter, navController: NavController) {
    val viewModel: LoginViewModel = hiltViewModel()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val snackbarManager = SnackbarManager(snackbarHostState, coroutineScope)

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        // Place other Scaffold slots if necessary
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            LoginScreen(
                viewModel = viewModel, logo = logo, snackbarManager = snackbarManager, navController
            )
        }
    }
}


@Composable
fun LoginScreen(viewModel: LoginViewModel, logo: Painter, snackbarManager: SnackbarManager, navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoggingIn by remember { mutableStateOf(false) }

    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+" // Simple email pattern for validation
    val isEmailValid = email.matches(emailPattern.toRegex())
    val isPasswordValid = password.length >= 8 // Assuming valid password is more than 5 characters

    val loginState by viewModel.loginState.collectAsState(initial = null)

    val invalidMessage = stringResource(id = R.string.invalid_email_pass)

    // This is where navigation should be handled based on the login state
    LaunchedEffect(loginState) {
        when (loginState) {
            true -> {
                isLoggingIn = false
                // Navigate to home screen
                navController.navigate("home")
            }

            false -> {
                isLoggingIn = false
            }

            null -> {
                println("State Reset")
            }
        }
    }

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
                value = email,
                onValueChange = { email = it },
                label = { Text(stringResource(id = R.string.email_text)) },
                singleLine = true,
//                keyboardOptions = KeyboardOptions.Default.copy(
//                    imeAction = ImeAction.Next
//                ),
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
//                keyboardOptions = KeyboardOptions.Default.copy(
//                    imeAction = ImeAction.Done
//                ),
                keyboardActions = KeyboardActions(onDone = {
                    isLoggingIn = true
                    viewModel.login(email, password, snackbarManager)
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
                    isLoggingIn = true
                    viewModel.login(email, password, snackbarManager)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary, contentColor = Color.Black
                ),
                enabled = !isLoggingIn && isEmailValid && isPasswordValid,

                modifier = Modifier
                    .height(50.dp)
                    .width(270.dp)
                    .background(LogoYellow, CircleShape)
            ) {
                if (isLoggingIn) {
                    Text(stringResource(id = R.string.logging_in_text), fontSize = 18.sp)

                } else {
                    Text(stringResource(id = R.string.login_text), fontSize = 18.sp)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            TextButton(onClick = {
                navController.navigate("register")
            }) {
                Text(
                    stringResource(id = R.string.new_user_register),
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 16.sp
                )
            }
        }
    }
}
