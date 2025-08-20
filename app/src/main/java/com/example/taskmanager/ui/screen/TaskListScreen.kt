package com.example.taskmanager.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import com.example.taskmanager.model.Task
import com.example.taskmanager.repository.TaskRepository
import com.example.taskmanager.ui.components.TaskItem

@Composable
fun TaskListScreen(navToAddTask: () -> Unit) {
    val repository = TaskRepository()
    var tasks by remember { mutableStateOf(listOf<Task>()) }

    LaunchedEffect(Unit) {
        tasks = repository.getTasks()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = navToAddTask) { Text("+") }
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(tasks) { task -> TaskItem(task) }
        }
    }
}
