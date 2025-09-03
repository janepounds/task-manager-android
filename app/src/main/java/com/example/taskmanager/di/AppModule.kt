package com.example.taskmanager.di

import android.content.Context
import com.example.taskmanager.network.AuthInterceptor
import com.example.taskmanager.network.RetrofitInstance
import com.example.taskmanager.network.TaskApi
import com.example.taskmanager.repository.TaskRepository
import com.example.taskmanager.utils.UserPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideTaskApi(): TaskApi {
        return RetrofitInstance.api // or build via Retrofit here
    }

    @Provides
    @Singleton
    fun provideTaskRepository(
        api: TaskApi,
        userPreferences: UserPreferences
    ): TaskRepository {
        return TaskRepository(api, userPreferences)
    }

    @Provides
    @Singleton
    fun provideUserPreferences(@ApplicationContext context: Context): UserPreferences {
        return UserPreferences(context)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        userPreferences: UserPreferences
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(userPreferences))
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }
}