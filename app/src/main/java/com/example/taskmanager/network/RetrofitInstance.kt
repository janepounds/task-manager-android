package com.example.taskmanager.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()


    val api: TaskApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://e2d2501efe4a.ngrok-free.app/") // emulator localhost
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TaskApi::class.java)
    }
}
