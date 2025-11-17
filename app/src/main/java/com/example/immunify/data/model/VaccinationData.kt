package com.example.immunify.data.model

import kotlinx.serialization.Serializable

@Serializable
data class VaccinationData(
    val id: String,
    val vaccinant: String,
    val immunizationType: String,
    val lotNumber: String,
    val dose: String,
    val administrator: String,
    val vaccine: VaccineData
)