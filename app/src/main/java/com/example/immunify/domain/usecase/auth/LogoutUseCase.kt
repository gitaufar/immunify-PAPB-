package com.example.immunify.domain.usecase.auth

import com.example.immunify.domain.repository.AuthRepository

class LogoutUseCase(
    private val repository: AuthRepository
) {
    operator fun invoke() = repository.logout()
}
