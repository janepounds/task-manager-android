package com.example.taskmanager.repository

import com.example.taskmanager.model.Task
import com.example.taskmanager.model.response.AuthRequest
import com.example.taskmanager.model.response.AuthResponse
import com.example.taskmanager.network.RetrofitInstance
import com.example.taskmanager.network.TaskApi
import com.example.taskmanager.utils.UserPreferences
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class TaskRepository @Inject constructor(private val api: TaskApi, private val userPreferences: UserPreferences) {


    suspend fun login(username: String, password: String): AuthResponse {
        val response = api.login(AuthRequest(username, password))
        // Save token locally
        userPreferences.saveToken(response.token)
        return response
    }

    suspend fun register(username: String, password: String): AuthResponse {
        val response = api.register(AuthRequest(username, password))
        userPreferences.saveToken(response.token)
        return response
    }

    // Assuming you have a way to get stored JWT token

    suspend fun getTasks(): List<Task> {
        val tokenString = userPreferences.token.firstOrNull() ?: ""
        return api.getTasks("Bearer $tokenString")
    }

    suspend fun addTask(task: Task): Task {
        val tokenString = userPreferences.token.firstOrNull() ?: ""
        return api.createTask("Bearer $tokenString", task)
    }

    suspend fun updateTask(task: Task): Task {
        return api.updateTask(task.id, task, userPreferences.token.toString())
    }

    suspend fun deleteTask(task: Task) {
        val tokenString = userPreferences.token.firstOrNull() ?: ""
        api.deleteTask(task.id, "Bearer $tokenString")
    }


}
