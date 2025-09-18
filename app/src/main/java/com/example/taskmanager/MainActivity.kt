package com.example.taskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskmanager.ui.screen.AddTaskScreen
import com.example.taskmanager.ui.screen.LoginScreen
import com.example.taskmanager.ui.screen.SignUpScreen
import com.example.taskmanager.ui.screen.SplashScreen
import com.example.taskmanager.ui.screen.TaskListScreen
import com.example.taskmanager.ui.theme.TaskManagerAppTheme
import com.example.taskmanager.utils.UserPreferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "splash") {
                composable("splash") {
                    SplashScreen(
                    viewModel = hiltViewModel(),
                        navController = navController,

                    )
                }
                composable("login") {
                    LoginScreen(viewModel = hiltViewModel(),
                        onLoginSuccess = { navController.navigate("taskList") { popUpTo("login") { inclusive = true } } },
                        navToSignUp = { navController.navigate("signup") },
                    )
                }


                // Sign Up
                composable("signup") {
                    SignUpScreen(
                        viewModel = hiltViewModel(),
                        onSignUpSuccess = {
                            navController.navigate("taskList") {
                                popUpTo("signup") { inclusive = true }
                            }
                        },
                        navToLogin = {
                            navController.navigate("login") {
                                popUpTo("signup") { inclusive = true }
                            }
                        }
                    )
                }
                composable("taskList") {
                    TaskListScreen(
                        navController = navController,
                        navToAddTask = { navController.navigate("addTask") },
                        navToUpdateTask = { task -> navController.navigate("updateTask/${task.id}") },
                        onDeleteTask = { task -> /* delete via ViewModel */ }
                    )
                }


                composable("addTask") { AddTaskScreen { navController.popBackStack() } }
            }
        }
    }
}
