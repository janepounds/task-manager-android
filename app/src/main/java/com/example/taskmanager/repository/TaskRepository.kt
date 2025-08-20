package com.example.taskmanager.repository

import com.example.taskmanager.model.Task
import com.example.taskmanager.network.RetrofitInstance

class TaskRepository {
    suspend fun getTasks() = RetrofitInstance.api.getTasks()
    suspend fun addTask(task: Task) = RetrofitInstance.api.createTask(task)
}
