package com.example.immunify.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ClinicData(
    val id: String,
    val name: String,
    val imageUrl: String,
    val address: String,
    val district: String,
    val city: String,
    val contact: String?,
    val latitude: Double,
    val longitude: Double,
    val rating: Double,
    val website: String?,
    val openingHours: String?,
    val availableVaccines: List<VaccineData>
)