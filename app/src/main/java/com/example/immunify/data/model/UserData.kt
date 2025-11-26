package com.example.immunify.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val id: String,
    val name: String,
    val email: String,
    val password: String,
    val phoneNumber: String?,
    val children: List<ChildData> = emptyList()
)