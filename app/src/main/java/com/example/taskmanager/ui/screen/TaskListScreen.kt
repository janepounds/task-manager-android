package com.example.taskmanager.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskmanager.model.Task
import com.example.taskmanager.repository.TaskRepository
import com.example.taskmanager.ui.components.TaskItem
import com.example.taskmanager.viewmodel.TaskViewModel

@Composable
fun TaskListScreen(
    viewModel: TaskViewModel = hiltViewModel(),
    navToAddTask: () -> Unit
) {
    // Collect tasks from ViewModel (StateFlow)
    val tasks by viewModel.tasks.collectAsState()

    // Trigger loading when screen opens
    LaunchedEffect(Unit) {
        viewModel.loadTasks()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = navToAddTask) {
                Text("+")
            }
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(tasks) { task ->
                TaskItem(task)
            }
        }
    }
}
