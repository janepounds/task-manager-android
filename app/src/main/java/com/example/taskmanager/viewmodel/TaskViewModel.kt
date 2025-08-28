package com.example.taskmanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.model.Task
import com.example.taskmanager.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val repository: TaskRepository) : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    fun loadTasks() {
        viewModelScope.launch {
            val result = repository.getTasks()
            _tasks.value = result
        }
    }

    fun addTask(task: Task, onDone: () -> Unit) {
        viewModelScope.launch {
            repository.addTask(task)
            onDone()
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            val updatedTask = repository.updateTask( task)
            _tasks.value = _tasks.value.map { if(it.id == task.id) updatedTask else it }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
            _tasks.value = _tasks.value.filter { it.id != task.id }
        }
    }
}