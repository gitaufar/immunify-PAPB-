package com.example.immunify.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.immunify.domain.usecase.auth.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoginSuccess = MutableStateFlow(false)
    val isLoginSuccess: StateFlow<Boolean> = _isLoginSuccess

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val result = authUseCases.login(email, password)

            result.onSuccess {
                _isLoginSuccess.value = true
            }.onFailure {
                _errorMessage.value = it.message ?: "Login failed"
            }

            _isLoading.value = false
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val result = authUseCases.register(email, password)

            result.onSuccess {
                _isLoginSuccess.value = true
            }.onFailure {
                _errorMessage.value = it.message ?: "Register failed"
            }

            _isLoading.value = false
        }
    }

    fun logout() = authUseCases.logout()

    fun getUser() = authUseCases.getCurrentUser()
}
