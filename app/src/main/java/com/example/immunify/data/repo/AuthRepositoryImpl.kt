package com.example.immunify.data.repository

import com.example.immunify.data.firebase.auth.AuthDatasource
import com.example.immunify.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser

class AuthRepositoryImpl(
    private val authDatasource: AuthDatasource
) : AuthRepository {

    override suspend fun register(email: String, password: String): Result<FirebaseUser?> {
        return authDatasource.register(email, password)
    }

    override suspend fun login(email: String, password: String): Result<FirebaseUser?> {
        return authDatasource.login(email, password)
    }

    override fun logout() {
        authDatasource.logout()
    }

    override fun getCurrentUser(): FirebaseUser? {
        return authDatasource.getCurrentUser()
    }

    override fun isUserLoggedIn(): Boolean {
        return authDatasource.isLoggedIn()
    }
}
