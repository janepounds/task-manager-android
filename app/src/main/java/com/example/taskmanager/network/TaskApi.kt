package com.example.taskmanager.network

import com.example.taskmanager.model.Task
import com.example.taskmanager.model.response.AuthRequest
import com.example.taskmanager.model.response.AuthResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface TaskApi {

    @POST("api/auth/login")
    suspend fun login(@Body request: AuthRequest): AuthResponse

    @POST("api/auth/register")
    suspend fun register(@Body request: AuthRequest): AuthResponse

    @GET("api/tasks")
    suspend fun getTasks(
        @Header("Authorization") token: String
    ): List<Task>

    @POST("api/tasks")
    suspend fun createTask(
        @Header("Authorization") token: String,
        @Body task: Task): Task

    @PUT("tasks/{id}")
    suspend fun updateTask(
        @Path("id") id: Long,
        @Body task: Task,
        @Header("Authorization") token: String
    ): Task

    @DELETE("tasks/{id}")
    suspend fun deleteTask(
        @Path("id") id: Long,
        @Header("Authorization") token: String
    )
}
