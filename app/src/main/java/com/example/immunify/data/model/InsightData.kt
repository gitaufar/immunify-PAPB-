package com.example.immunify.data.model

data class InsightData(
    val id: String,
    val title: String,
    val imageUrl: String,
    val description: String,
    val date: String,
    val content: List<String>
)