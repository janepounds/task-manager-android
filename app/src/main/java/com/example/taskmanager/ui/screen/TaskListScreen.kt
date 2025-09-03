package com.example.taskmanager.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.taskmanager.model.Task
import com.example.taskmanager.repository.TaskRepository
import com.example.taskmanager.ui.components.TaskItem
import com.example.taskmanager.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    viewModel: TaskViewModel = hiltViewModel(),
    navController: NavController,
    navToAddTask: () -> Unit,
    navToUpdateTask: (Task) -> Unit,
    onDeleteTask: (Task) -> Unit = {}
) {
    val tasks by viewModel.tasks.collectAsState()



    LaunchedEffect(Unit) {
        viewModel.loadTasks()
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("My Tasks") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = navToAddTask) { Text("+") }
        }
    ) { paddingValues ->
        if (tasks.isEmpty()) {
            Box(
                modifier = Modifier.padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("No tasks yet. Add one!")
            }
        } else {
            LazyColumn(modifier = Modifier.padding(paddingValues)) {
                items(tasks) { task ->
                    TaskItem(
                        task = task,
                        onClick = { navToUpdateTask(task) },
                        onDelete = { onDeleteTask(task) }
                    )
                }
            }
        }
    }
}
