package com.example.immunify.domain.usecase.auth

import com.example.immunify.domain.repo.AuthRepository

class LoginUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String) =
        repository.login(email, password)
}