package com.example.taskmanager.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskmanager.model.Task
import com.example.taskmanager.repository.TaskRepository
import com.example.taskmanager.viewmodel.TaskViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun AddTaskScreen(
    viewModel: TaskViewModel = hiltViewModel(),
    navBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") }
        )
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") }
        )
        Button(
            onClick = {
                viewModel.addTask(Task(title = title, description = description)) {
                    navBack()
                }
            }
        ) {
            Text("Add Task")
        }
    }
}

