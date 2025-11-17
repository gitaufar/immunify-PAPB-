package com.example.immunify.domain.usecase

import com.example.immunify.domain.model.Appointment
import com.example.immunify.domain.repo.AppointmentRepository
import com.example.immunify.util.Result
import javax.inject.Inject

/**
 * Use Case untuk get appointments by user
 */
class GetUserAppointmentsUseCase @Inject constructor(
    private val appointmentRepository: AppointmentRepository
) {
    suspend operator fun invoke(userId: String): Result<List<Appointment>> {
        if (userId.isEmpty()) {
            return Result.Error(Exception("User ID is required"))
        }
        
        return appointmentRepository.getAppointmentsByUserId(userId)
    }
}
