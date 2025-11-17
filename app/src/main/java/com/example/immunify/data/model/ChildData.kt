package com.example.immunify.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ChildData(
    val id: String,
    val name: String,
    val birthDate: String,
    val gender: String,
    val vaccinationHistory: List<VaccinationData> = emptyList()
)