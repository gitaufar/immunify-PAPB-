package com.example.immunify.data.repo

import com.example.immunify.data.firebase.auth.AuthDatasource
import com.example.immunify.data.model.UserData
import com.example.immunify.domain.repo.AuthRepository
import com.google.firebase.auth.FirebaseUser

class AuthRepositoryImpl(
    private val authDatasource: AuthDatasource
) : AuthRepository {

    override suspend fun register(username: String, email: String, password: String): Result<FirebaseUser?> {
        return authDatasource.register(username, email, password)
    }

    override suspend fun login(email: String, password: String): Result<FirebaseUser?> {
        return authDatasource.login(email, password)
    }

    override fun logout() {
        authDatasource.logout()
    }

    override suspend fun getCurrentUser(): UserData? {
        return authDatasource.getCurrentUser()
    }

    override fun isUserLoggedIn(): Boolean {
        return authDatasource.isLoggedIn()
    }
}
