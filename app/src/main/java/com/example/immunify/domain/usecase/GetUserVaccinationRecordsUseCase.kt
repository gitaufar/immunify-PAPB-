package com.example.immunify.domain.usecase

import com.example.immunify.domain.model.VaccinationRecord
import com.example.immunify.domain.repo.VaccinationRecordRepository
import com.example.immunify.util.Result
import javax.inject.Inject

/**
 * Use case untuk get vaccination records by user ID
 */
class GetUserVaccinationRecordsUseCase @Inject constructor(
    private val repository: VaccinationRecordRepository
) {
    suspend operator fun invoke(userId: String): Result<List<VaccinationRecord>> {
        if (userId.isBlank()) {
            return Result.Error(Exception("User ID is required"))
        }
        
        return repository.getVaccinationRecordsByUserId(userId)
    }
}
