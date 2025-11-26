package com.example.immunify.domain.usecase.auth

import com.example.immunify.domain.repo.AuthRepository

class LogoutUseCase(
    private val repository: AuthRepository
) {
    operator fun invoke() = repository.logout()
}
