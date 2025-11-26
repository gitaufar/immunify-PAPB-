package com.example.immunify.data.model

import kotlinx.serialization.Serializable

@Serializable
data class VaccineData(
    val id: String,
    val name: String,
    val description: List<String> = emptyList(),
    val brand: List<String> = emptyList(),
    val scheduledDates: List<String> = emptyList(),
    val completedDates: List<String> = emptyList(),
    val remainingDoses: Int = 0
)
