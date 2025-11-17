package com.example.immunify.domain.repo

import com.example.immunify.domain.model.Appointment
import com.example.immunify.util.Result

/**
 * Repository interface untuk Appointment
 * Abstraksi untuk operasi data appointment
 */
interface AppointmentRepository {
    
    suspend fun createAppointment(appointment: Appointment): Result<String>
    
    suspend fun getAppointmentById(appointmentId: String): Result<Appointment>
    
    suspend fun getAppointmentsByUserId(userId: String): Result<List<Appointment>>
    
    suspend fun updateAppointmentStatus(appointmentId: String, status: String): Result<Unit>
    
    suspend fun cancelAppointment(appointmentId: String): Result<Unit>
    
    suspend fun getAllAppointments(): Result<List<Appointment>>
}
