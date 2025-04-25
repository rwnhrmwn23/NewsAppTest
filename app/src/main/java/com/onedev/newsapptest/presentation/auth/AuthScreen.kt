package com.onedev.newsapptest.presentation.auth

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.onedev.newsapptest.presentation.navigation.Screen
import com.onedev.newsapptest.utils.AutoLogoutWorker
import com.onedev.newsapptest.utils.UserPreferencesManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@Composable
fun EntryPointScreen(
    context: Context,
    navController: NavHostController
) {
    val isLoggedIn by UserPreferencesManager.isLoggedIn(context).collectAsState(initial = false)

    LaunchedEffect(isLoggedIn) {
        delay(1000)
        if (isLoggedIn) {
            navController.navigate(Screen.Home.route)
        } else {
            navController.navigate(Screen.Auth.route)
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Welcome...")
    }
}

@Composable
fun AuthScreen(context: Context, onLoginSuccess: () -> Unit) {
    var isLoginMode by remember { mutableStateOf(true) }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome", style = MaterialTheme.typography.headlineMedium)

        if (!isLoginMode) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
        }

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        PasswordTextField(
            password = password,
            onPasswordChange = { password = it }
        )

        Spacer(Modifier.height(16.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if (isLoginMode) {
                    coroutineScope.launch {
                        val success = UserPreferencesManager.loginUser(context, email, password)
                        if (success) {
                            onLoginSuccess()
                            scheduleAutoLogout(context)
                        }
                        else message = "Invalid credentials"
                    }
                } else {
                    coroutineScope.launch {
                        UserPreferencesManager.saveUser(context, name, email, password)
                        onLoginSuccess()
                        scheduleAutoLogout(context)
                    }
                }

            }) {
            Text(if (isLoginMode) "Login" else "Register")
        }

        Spacer(Modifier.height(16.dp))
        Text(
            text = if (isLoginMode) "Don't have an account? Register" else "Already have an account? Login",
            modifier = Modifier.clickable { isLoginMode = !isLoginMode }
        )

        if (message.isNotEmpty()) {
            Spacer(Modifier.height(12.dp))
            Text(text = message, color = Color.Red)
        }
    }
}

@Composable
fun PasswordTextField(
    password: String,
    onPasswordChange: (String) -> Unit
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text("Password") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image =
                if (isPasswordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
            val description = if (isPasswordVisible) "Hide password" else "Show password"

            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                Icon(imageVector = image, contentDescription = description)
            }
        }
    )
}

fun scheduleAutoLogout(context: Context, delayInSeconds: Long = 600) {
    val workRequest = OneTimeWorkRequestBuilder<AutoLogoutWorker>()
        .setInitialDelay(delayInSeconds, TimeUnit.SECONDS) // Use 600 for 10 minutes
        .build()

    WorkManager.getInstance(context).enqueueUniqueWork(
        "auto_logout",
        ExistingWorkPolicy.REPLACE,
        workRequest
    )
}


