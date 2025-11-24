package com.example.immunify.domain.usecase

import com.example.immunify.data.repo.AppointmentRepositoryImpl
import com.example.immunify.util.Result
import javax.inject.Inject

/**
 * Use Case untuk cancel appointment
 * Requires userId karena struktur Firestore nested
 */
class CancelAppointmentUseCase @Inject constructor(
    private val appointmentRepository: AppointmentRepositoryImpl
) {
    suspend operator fun invoke(userId: String, appointmentId: String): Result<Unit> {
        if (userId.isEmpty()) {
            return Result.Error(Exception("User ID is required"))
        }
        
        if (appointmentId.isEmpty()) {
            return Result.Error(Exception("Appointment ID is required"))
        }
        
        return appointmentRepository.cancelAppointmentByUser(userId, appointmentId)
    }
}
