package com.example.immunify.data.firebase.dto

/**
 * DTO untuk Child di Firestore
 */
data class ChildDto(
    val id: String = "",
    val userId: String = "",
    val name: String = "",
    val birthDate: String = "",
    val gender: String = "",
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L
)
