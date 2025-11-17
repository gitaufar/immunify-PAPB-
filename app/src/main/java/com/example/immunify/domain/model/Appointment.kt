package com.example.immunify.domain.model

import com.example.immunify.data.model.AppointmentStatus

/**
 * Domain model untuk Appointment
 * Model ini digunakan di layer domain/use case
 */
data class Appointment(
    val id: String = "",
    val userId: String,
    val userName: String,
    val userPhone: String,
    val clinicId: String,
    val clinicName: String,
    val clinicAddress: String,
    val date: String,
    val timeSlot: String,
    val vaccineId: String,
    val vaccineName: String,
    val vaccinantIds: List<String>,
    val vaccinantNames: List<String>,
    val status: AppointmentStatus = AppointmentStatus.PENDING,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
