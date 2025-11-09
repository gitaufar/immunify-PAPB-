package com.example.immunify.data.model

data class VaccineData(
    val id: String,
    val name: String,
    val description: List<String> = emptyList(),
    val brand: List<String> = emptyList(),
    val scheduledDates: List<String> = emptyList(),
    val remainingDoses: Int = 0
)