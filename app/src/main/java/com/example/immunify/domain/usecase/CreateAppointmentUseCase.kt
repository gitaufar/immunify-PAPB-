package com.example.immunify.domain.usecase

import com.example.immunify.domain.model.Appointment
import com.example.immunify.domain.repo.AppointmentRepository
import com.example.immunify.util.Result
import javax.inject.Inject

/**
 * Use Case untuk create appointment
 * Mengikuti Clean Architecture principle
 */
class CreateAppointmentUseCase @Inject constructor(
    private val appointmentRepository: AppointmentRepository
) {
    suspend operator fun invoke(appointment: Appointment): Result<String> {
        // Validasi data sebelum menyimpan
        if (appointment.userId.isEmpty()) {
            return Result.Error(Exception("User ID is required"))
        }
        
        if (appointment.clinicId.isEmpty()) {
            return Result.Error(Exception("Clinic ID is required"))
        }
        
        if (appointment.date.isEmpty()) {
            return Result.Error(Exception("Date is required"))
        }
        
        if (appointment.timeSlot.isEmpty()) {
            return Result.Error(Exception("Time slot is required"))
        }
        
        if (appointment.vaccinantIds.isEmpty()) {
            return Result.Error(Exception("At least one vaccinant is required"))
        }
        
        return appointmentRepository.createAppointment(appointment)
    }
}
