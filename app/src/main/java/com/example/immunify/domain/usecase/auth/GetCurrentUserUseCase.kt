package com.example.immunify.domain.usecase.auth

import com.example.immunify.domain.repo.AuthRepository

class GetCurrentUserUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() = repository.getCurrentUser()
}
