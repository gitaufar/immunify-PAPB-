package com.example.immunify.domain.model

/**
 * Domain model untuk Child
 */
data class Child(
    val id: String = "",
    val userId: String = "",
    val name: String = "",
    val birthDate: String = "", // Format: yyyy-MM-dd
    val gender: String = "", // "Male" or "Female"
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
