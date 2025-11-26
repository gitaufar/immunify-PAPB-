package com.example.immunify.data.repo

import com.example.immunify.data.firebase.dto.VaccinationRecordDto
import com.example.immunify.data.firebase.firestore.FirebaseFirestoreDatasource
import com.example.immunify.data.mapper.VaccinationRecordMapper.toDomain
import com.example.immunify.data.mapper.VaccinationRecordMapper.toDto
import com.example.immunify.domain.model.VaccinationRecord
import com.example.immunify.domain.repo.VaccinationRecordRepository
import com.example.immunify.util.Result
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * Implementation of VaccinationRecordRepository
 * Structure: users/{userId}/vaccinationRecords/{recordId}
 */
class VaccinationRecordRepositoryImpl @Inject constructor(
    private val firestoreDatasource: FirebaseFirestoreDatasource
) : VaccinationRecordRepository {

    companion object {
        private const val COLLECTION_USERS = "users"
        private const val SUBCOLLECTION_VACCINATION_RECORDS = "vaccinationRecords"
    }

    override suspend fun createVaccinationRecord(record: VaccinationRecord): Result<String> {
        return try {
            val recordDto = record.toDto()
            
            // Generate ID if not exists
            val docId = recordDto.id.ifEmpty {
                firestoreDatasource.firestore
                    .collection(COLLECTION_USERS)
                    .document(record.userId)
                    .collection(SUBCOLLECTION_VACCINATION_RECORDS)
                    .document()
                    .id
            }

            val finalDto = recordDto.copy(id = docId)
            
            // Save to: users/{userId}/vaccinationRecords/{recordId}
            firestoreDatasource.firestore
                .collection(COLLECTION_USERS)
                .document(record.userId)
                .collection(SUBCOLLECTION_VACCINATION_RECORDS)
                .document(docId)
                .set(finalDto)
                .await()
            
            Result.Success(docId)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getVaccinationRecordsByUserId(userId: String): Result<List<VaccinationRecord>> {
        return try {
            val snapshot = firestoreDatasource.firestore
                .collection(COLLECTION_USERS)
                .document(userId)
                .collection(SUBCOLLECTION_VACCINATION_RECORDS)
                .orderBy("vaccinationDate", Query.Direction.DESCENDING)
                .get()
                .await()

            val records = snapshot.toObjects(VaccinationRecordDto::class.java)
                .map { it.toDomain() }
            
            Result.Success(records)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getVaccinationRecordsByChildId(
        userId: String,
        childId: String
    ): Result<List<VaccinationRecord>> {
        return try {
            val snapshot = firestoreDatasource.firestore
                .collection(COLLECTION_USERS)
                .document(userId)
                .collection(SUBCOLLECTION_VACCINATION_RECORDS)
                .whereEqualTo("childId", childId)
                .orderBy("vaccinationDate", Query.Direction.DESCENDING)
                .get()
                .await()

            val records = snapshot.toObjects(VaccinationRecordDto::class.java)
                .map { it.toDomain() }
            
            Result.Success(records)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun updateVaccinationRecord(record: VaccinationRecord): Result<Unit> {
        return try {
            val recordDto = record.toDto()
            
            firestoreDatasource.firestore
                .collection(COLLECTION_USERS)
                .document(record.userId)
                .collection(SUBCOLLECTION_VACCINATION_RECORDS)
                .document(record.id)
                .set(recordDto)
                .await()
            
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun deleteVaccinationRecord(userId: String, recordId: String): Result<Unit> {
        return try {
            firestoreDatasource.firestore
                .collection(COLLECTION_USERS)
                .document(userId)
                .collection(SUBCOLLECTION_VACCINATION_RECORDS)
                .document(recordId)
                .delete()
                .await()
            
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
