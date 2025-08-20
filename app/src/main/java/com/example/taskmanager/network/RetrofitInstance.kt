package com.example.taskmanager.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: TaskApi by lazy {
        Retrofit.Builder()
            .baseUrl("http://127.0.0:8081/") // emulator localhost
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TaskApi::class.java)
    }
}
