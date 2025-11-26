package com.example.immunify.data.firebase.dto

import com.example.immunify.data.model.AppointmentStatus

/**
 * DTO (Data Transfer Object) untuk Firestore
 * Model ini yang akan disimpan ke Firestore
 */
data class AppointmentDto(
    val id: String = "",
    val userId: String = "",
    val userName: String = "",
    val userPhone: String = "",
    val clinicId: String = "",
    val clinicName: String = "",
    val clinicAddress: String = "",
    val date: String = "",
    val timeSlot: String = "",
    val vaccineId: String = "",
    val vaccineName: String = "",
    val vaccinantIds: List<String> = emptyList(),
    val vaccinantNames: List<String> = emptyList(),
    val status: String = AppointmentStatus.PENDING.name,
    val lotNumber: String = "",
    val dose: String = "",
    val administrator: String = "",
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L
) {
    // No-arg constructor untuk Firestore
    constructor() : this(
        id = "",
        userId = "",
        userName = "",
        userPhone = "",
        clinicId = "",
        clinicName = "",
        clinicAddress = "",
        date = "",
        timeSlot = "",
        vaccineId = "",
        vaccineName = "",
        vaccinantIds = emptyList(),
        vaccinantNames = emptyList(),
        status = AppointmentStatus.PENDING.name,
        lotNumber = "",
        dose = "",
        administrator = "",
        createdAt = 0L,
        updatedAt = 0L
    )
}
