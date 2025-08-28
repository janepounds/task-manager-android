package com.example.taskmanager.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.taskmanager.viewmodel.AuthViewModel

@Composable
fun LoginScreen(viewModel: AuthViewModel, onLoginSuccess: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val token by viewModel.authToken.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(token) {
        if (token != null) onLoginSuccess()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(value = username, onValueChange = { username = it }, label = { Text("Username") })
        TextField(value = password, onValueChange = { password = it }, label = { Text("Password") })
        Button(onClick = { viewModel.login(username, password) }) { Text("Login") }
        error?.let { Text(it, color = Color.Red) }
    }
}