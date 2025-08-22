package com.example.taskmanager.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: TaskApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://b4e7f51d147e.ngrok-free.app/") // emulator localhost
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TaskApi::class.java)
    }
}
