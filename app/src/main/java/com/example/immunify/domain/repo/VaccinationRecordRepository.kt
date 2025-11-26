package com.example.immunify.domain.repo

import com.example.immunify.domain.model.VaccinationRecord
import com.example.immunify.util.Result

/**
 * Repository interface untuk VaccinationRecord
 */
interface VaccinationRecordRepository {
    
    suspend fun createVaccinationRecord(record: VaccinationRecord): Result<String>
    
    suspend fun getVaccinationRecordsByUserId(userId: String): Result<List<VaccinationRecord>>
    
    suspend fun getVaccinationRecordsByChildId(userId: String, childId: String): Result<List<VaccinationRecord>>
    
    suspend fun updateVaccinationRecord(record: VaccinationRecord): Result<Unit>
    
    suspend fun deleteVaccinationRecord(userId: String, recordId: String): Result<Unit>
}
