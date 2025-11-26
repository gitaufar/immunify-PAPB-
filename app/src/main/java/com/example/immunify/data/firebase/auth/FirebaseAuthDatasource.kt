package com.example.immunify.data.firebase.auth

import com.example.immunify.data.firebase.firestore.FirebaseFirestoreDatasource
import com.example.immunify.data.model.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import java.security.MessageDigest

fun hashPassword(password: String): String {
    val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
    return bytes.joinToString("") { "%02x".format(it) }
}

class FirebaseAuthDatasource(
    private val firebaseAuth: FirebaseAuth
) : AuthDatasource {

    override suspend fun register(username: String, email: String, password: String): Result<FirebaseUser?> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()

            val userDto = com.example.immunify.data.firebase.dto.UserDto(
                id = result.user?.uid ?: "no id",
                name = username,
                email = email,
                password = hashPassword(password),
                phoneNumber = null,
                children = emptyList()
            )

            FirebaseFirestoreDatasource().setDocument<com.example.immunify.data.firebase.dto.UserDto>(
                collection = "users",
                documentId = userDto.id,
                data = userDto
            )
            Result.success(result.user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun login(email: String, password: String): Result<FirebaseUser?> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.success(result.user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }

    override suspend fun getCurrentUser(): UserData? {
        return try {
            val currentUser = firebaseAuth.currentUser
            android.util.Log.d("FirebaseAuth", "getCurrentUser - FirebaseAuth.currentUser: $currentUser")
            
            if (currentUser == null) {
                android.util.Log.d("FirebaseAuth", "No Firebase user logged in")
                return null
            }

            val userId = currentUser.uid
            android.util.Log.d("FirebaseAuth", "Fetching user data for userId: $userId")

            val userDto = FirebaseFirestoreDatasource()
                .getDocument<com.example.immunify.data.firebase.dto.UserDto>(
                    collection = "users",
                    documentId = userId
                )
            
            android.util.Log.d("FirebaseAuth", "Fetched userDto from Firestore: $userDto")
            
            // Convert UserDto to UserData (domain model)
            val userData = userDto?.let {
                UserData(
                    id = it.id,
                    name = it.name,
                    email = it.email,
                    password = it.password,
                    phoneNumber = it.phoneNumber,
                    children = it.children ?: emptyList()
                )
            }
            
            android.util.Log.d("FirebaseAuth", "Converted to UserData: $userData")
            return userData
        } catch (e: Exception) {
            android.util.Log.e("FirebaseAuth", "Error getting current user", e)
            null
        }
    }


    override fun isLoggedIn(): Boolean =
        firebaseAuth.currentUser != null
}
