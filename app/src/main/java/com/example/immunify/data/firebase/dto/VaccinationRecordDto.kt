package com.example.immunify.data.firebase.dto

/**
 * Firestore DTO untuk VaccinationRecord
 */
data class VaccinationRecordDto(
    var id: String = "",
    var userId: String = "",
    var childId: String = "",
    var childName: String = "",
    var vaccineId: String = "",
    var vaccineName: String = "",
    var lotNumber: String = "",
    var dose: String = "",
    var administrator: String = "",
    var vaccinationDate: String = "",
    var notes: String = "",
    var createdAt: Long = 0L,
    var updatedAt: Long = 0L
)
