package com.example.immunify.data.firebase.auth

import com.google.firebase.auth.FirebaseUser

interface AuthDatasource {

    suspend fun register(email: String, password: String): Result<FirebaseUser?>
    suspend fun login(email: String, password: String): Result<FirebaseUser?>
    fun logout()
    fun getCurrentUser(): FirebaseUser?
    fun isLoggedIn(): Boolean
}
