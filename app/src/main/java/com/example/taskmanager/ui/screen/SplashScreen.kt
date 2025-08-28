package com.example.taskmanager.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.taskmanager.utils.UserPreferences

@Composable
fun SplashScreen(
    userPreferences: UserPreferences,
    navToDashboard: () -> Unit,
    navToLogin: () -> Unit
) {
    val tokenFlow = userPreferences.token.collectAsState(initial = null)

    // Navigate once token is loaded
    LaunchedEffect(tokenFlow.value) {
        if (tokenFlow.value.isNullOrEmpty()) {
            navToLogin()
        } else {
            navToDashboard()
        }
    }

    // Simple splash UI
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "TaskManager", style = MaterialTheme.typography.bodyMedium)
    }
}
