package com.example.immunify.domain.usecase

import com.example.immunify.data.model.AppointmentStatus
import com.example.immunify.data.repo.AppointmentRepositoryImpl
import com.example.immunify.util.Result
import javax.inject.Inject

/**
 * Use case untuk complete appointment (mark as COMPLETED)
 */
class CompleteAppointmentUseCase @Inject constructor(
    private val repository: AppointmentRepositoryImpl
) {
    suspend operator fun invoke(
        userId: String,
        appointmentId: String,
        lotNumber: String = "",
        dose: String = "",
        administrator: String = ""
    ): Result<Unit> {
        if (userId.isBlank()) {
            return Result.Error(Exception("User ID is required"))
        }
        if (appointmentId.isBlank()) {
            return Result.Error(Exception("Appointment ID is required"))
        }
        
        return repository.updateAppointmentStatusByUser(
            userId = userId,
            appointmentId = appointmentId,
            status = AppointmentStatus.COMPLETED,
            lotNumber = lotNumber,
            dose = dose,
            administrator = administrator
        )
    }
}
