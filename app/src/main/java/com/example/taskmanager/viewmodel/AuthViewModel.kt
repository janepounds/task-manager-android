package com.example.taskmanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    private val _authToken = MutableStateFlow<String?>(null)
    val authToken: StateFlow<String?> = _authToken

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.login(username, password)
                _authToken.value = response.token
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun register(username: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.register(username, password)
                _authToken.value = response.token
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}