package com.example.immunify.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.immunify.data.model.UserData
import com.example.immunify.domain.usecase.auth.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private val _user = MutableStateFlow<UserData?>(null)
    val user: StateFlow<UserData?> = _user

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoginSuccess = MutableStateFlow(false)
    val isLoginSuccess: StateFlow<Boolean> = _isLoginSuccess

    init {
        android.util.Log.d("AuthViewModel", "Init: Starting to load current user")
        viewModelScope.launch {
            loadCurrentUser()
        }
    }

    private fun loadCurrentUser() {
        viewModelScope.launch {
            _isLoading.value = true
            android.util.Log.d("AuthViewModel", "Loading current user...")

            val userData = authUseCases.getCurrentUser()
            android.util.Log.d("AuthViewModel", "Current user loaded: $userData")
            android.util.Log.d("AuthViewModel", "User ID: ${userData?.id}, Name: ${userData?.name}")
            _user.value = userData

            _isLoading.value = false
        }
    }


    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val result = authUseCases.login(email, password)

            result.onSuccess {
                // Reload user data after successful login
                loadCurrentUser()
                _isLoginSuccess.value = true
            }.onFailure {
                _errorMessage.value = it.message ?: "Login failed"
            }

            _isLoading.value = false
        }
    }

    fun register(username: String, email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val result = authUseCases.register(username, email, password)

            result.onSuccess {
                // Reload user data after successful registration
                loadCurrentUser()
                _isLoginSuccess.value = true
            }.onFailure {
                _errorMessage.value = it.message ?: "Register failed"
            }

            _isLoading.value = false
        }
    }

    fun logout() = authUseCases.logout()

    suspend fun getUser() = authUseCases.getCurrentUser()
}
