package com.example.immunify.domain.usecase.auth

import com.example.immunify.domain.repository.AuthRepository

class GetCurrentUserUseCase(
    private val repository: AuthRepository
) {
    operator fun invoke() = repository.getCurrentUser()
}
