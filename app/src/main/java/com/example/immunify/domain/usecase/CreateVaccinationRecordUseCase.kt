package com.example.immunify.domain.usecase

import com.example.immunify.domain.model.VaccinationRecord
import com.example.immunify.domain.repo.VaccinationRecordRepository
import com.example.immunify.util.Result
import javax.inject.Inject

/**
 * Use case untuk create vaccination record
 */
class CreateVaccinationRecordUseCase @Inject constructor(
    private val repository: VaccinationRecordRepository
) {
    suspend operator fun invoke(record: VaccinationRecord): Result<String> {
        // Validation
        if (record.userId.isBlank()) {
            return Result.Error(Exception("User ID is required"))
        }
        if (record.childId.isBlank()) {
            return Result.Error(Exception("Child ID is required"))
        }
        if (record.vaccineName.isBlank()) {
            return Result.Error(Exception("Vaccine name is required"))
        }
        if (record.vaccinationDate.isBlank()) {
            return Result.Error(Exception("Vaccination date is required"))
        }
        
        return repository.createVaccinationRecord(record)
    }
}
