package com.example.immunify.domain.repo

import com.example.immunify.data.model.UserData
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {

    suspend fun register(username: String, email: String, password: String): Result<FirebaseUser?>
    suspend fun login(email: String, password: String): Result<FirebaseUser?>
    fun logout()
    suspend fun getCurrentUser(): UserData?
    fun isUserLoggedIn(): Boolean
}
