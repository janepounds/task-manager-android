package com.example.taskmanager.model

data class Task(
val id: Int? = null,
val title: String,
val description: String,
val completed: Boolean = false
)

