package com.example.immunify.domain.repository

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {

    suspend fun register(email: String, password: String): Result<FirebaseUser?>
    suspend fun login(email: String, password: String): Result<FirebaseUser?>
    fun logout()
    fun getCurrentUser(): FirebaseUser?
    fun isUserLoggedIn(): Boolean
}
