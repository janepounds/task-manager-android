package com.example.taskmanager.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.taskmanager.utils.UserPreferences.Companion.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(private val context: Context) {

    companion object {
        private val Context.dataStore by preferencesDataStore("user_prefs")
        val TOKEN_KEY = stringPreferencesKey("auth_token")
    }

    // Save token
    suspend fun saveToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
        }
    }

    // Retrieve token as Flow
    val token: Flow<String?> = context.dataStore.data
        .map { prefs -> prefs[TOKEN_KEY] }

    suspend fun clearToken() {
        context.dataStore.edit { it.remove(TOKEN_KEY) }
    }
}