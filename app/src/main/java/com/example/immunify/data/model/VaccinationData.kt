package com.example.immunify.data.model

data class VaccinationData(
    val id: String,
    val vaccinant: String,
    val immunizationType: String,
    val lotNumber: String,
    val dose: String,
    val administrator: String,
    val vaccine: VaccineData
)