package com.example.immunify.data.model

data class UserData(
    val id: String,
    val name: String,
    val email: String,
    val password: String,
    val phoneNumber: String,
    val children: List<ChildData> = emptyList()
)