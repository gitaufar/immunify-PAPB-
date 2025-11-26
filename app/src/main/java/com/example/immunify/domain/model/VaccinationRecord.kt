package com.example.immunify.domain.model

/**
 * Domain model untuk Vaccination Record
 */
data class VaccinationRecord(
    val id: String = "",
    val userId: String = "",
    val childId: String = "",
    val childName: String = "",
    val vaccineId: String = "",
    val vaccineName: String = "",
    val lotNumber: String = "",
    val dose: String = "",
    val administrator: String = "",
    val vaccinationDate: String = "", // Format: yyyy-MM-dd
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
