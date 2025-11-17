package com.example.immunify.domain.usecase

import com.example.immunify.domain.repo.AppointmentRepository
import com.example.immunify.util.Result
import javax.inject.Inject

/**
 * Use Case untuk cancel appointment
 */
class CancelAppointmentUseCase @Inject constructor(
    private val appointmentRepository: AppointmentRepository
) {
    suspend operator fun invoke(appointmentId: String): Result<Unit> {
        if (appointmentId.isEmpty()) {
            return Result.Error(Exception("Appointment ID is required"))
        }
        
        return appointmentRepository.cancelAppointment(appointmentId)
    }
}
