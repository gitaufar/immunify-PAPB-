package com.example.immunify.domain.usecase.auth

import com.example.immunify.domain.repo.AuthRepository

class RegisterUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(username: String, email: String, password: String) =
        repository.register(username, email, password)
}
