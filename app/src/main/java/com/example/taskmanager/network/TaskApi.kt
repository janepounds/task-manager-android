package com.example.taskmanager.network

import com.example.taskmanager.model.Task
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body

interface TaskApi {
    @GET("api/tasks")
    suspend fun getTasks(): List<Task>

    @POST("api/tasks")
    suspend fun createTask(@Body task: Task): Task
}
