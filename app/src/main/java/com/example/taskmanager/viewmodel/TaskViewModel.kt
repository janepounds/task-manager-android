package com.example.taskmanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.model.Task
import com.example.taskmanager.repository.TaskRepository
import com.example.taskmanager.utils.AuthEvent
import com.example.taskmanager.utils.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    // 🔹 Expose tasks
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    // 🔹 Expose token + auth events
    val tokenFlow: Flow<String?> = userPreferences.token
    val authEvents: SharedFlow<AuthEvent> = userPreferences.authEvents

    // Load all tasks
    fun loadTasks() {
        viewModelScope.launch {
            try {
                val result = repository.getTasks()
                _tasks.value = result
            } catch (e: Exception) {
                // You could emit an error event here
                _tasks.value = emptyList()
            }
        }
    }

    fun addTask(task: Task, onDone: () -> Unit) {
        viewModelScope.launch {
            repository.addTask(task)
            loadTasks()
            onDone()
        }
    }

    fun getTaskById(id: Int): Task? {
        return tasks.value.find { (it.id?: "") == id }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task)
            loadTasks()
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
            _tasks.value = _tasks.value.filter { it.id != task.id }
        }
    }

    // 🔹 Manage token
    fun saveToken(token: String) {
        viewModelScope.launch {
            userPreferences.saveToken(token)
        }
    }

    fun clearToken() {
        viewModelScope.launch {
            userPreferences.clearToken()
        }
    }
}