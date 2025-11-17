package com.example.immunify.data.repo

import com.example.immunify.data.firebase.dto.AppointmentDto
import com.example.immunify.data.firebase.firestore.FirebaseFirestoreDatasource
import com.example.immunify.data.mapper.AppointmentMapper.toDomain
import com.example.immunify.data.mapper.AppointmentMapper.toDto
import com.example.immunify.data.model.AppointmentStatus
import com.example.immunify.domain.model.Appointment
import com.example.immunify.domain.repo.AppointmentRepository
import com.example.immunify.util.Result
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * Implementasi AppointmentRepository
 * Bertanggung jawab untuk operasi CRUD appointment ke Firestore
 */
class AppointmentRepositoryImpl @Inject constructor(
    private val firestoreDatasource: FirebaseFirestoreDatasource
) : AppointmentRepository {

    companion object {
        private const val COLLECTION_APPOINTMENTS = "appointments"
    }

    override suspend fun createAppointment(appointment: Appointment): Result<String> {
        return try {
            val appointmentDto = appointment.toDto()
            
            // Generate ID jika belum ada
            val docId = if (appointmentDto.id.isEmpty()) {
                firestoreDatasource.firestore.collection(COLLECTION_APPOINTMENTS)
                    .document().id
            } else {
                appointmentDto.id
            }

            val finalDto = appointmentDto.copy(id = docId)
            
            firestoreDatasource.setDocument<AppointmentDto>(
                collection = COLLECTION_APPOINTMENTS,
                documentId = docId,
                data = finalDto
            )
            
            Result.Success(docId)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getAppointmentById(appointmentId: String): Result<Appointment> {
        return try {
            val dto = firestoreDatasource.getDocument<AppointmentDto>(
                collection = COLLECTION_APPOINTMENTS,
                documentId = appointmentId
            )
            
            if (dto != null) {
                Result.Success(dto.toDomain())
            } else {
                Result.Error(Exception("Appointment not found"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getAppointmentsByUserId(userId: String): Result<List<Appointment>> {
        return try {
            val snapshot = firestoreDatasource.firestore.collection(COLLECTION_APPOINTMENTS)
                .whereEqualTo("userId", userId)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .await()

            val appointments = snapshot.toObjects(AppointmentDto::class.java)
                .map { it.toDomain() }
            
            Result.Success(appointments)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun updateAppointmentStatus(
        appointmentId: String,
        status: String
    ): Result<Unit> {
        return try {
            firestoreDatasource.updateDocument(
                collection = COLLECTION_APPOINTMENTS,
                documentId = appointmentId,
                fields = mapOf(
                    "status" to status,
                    "updatedAt" to System.currentTimeMillis()
                )
            )
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun cancelAppointment(appointmentId: String): Result<Unit> {
        return updateAppointmentStatus(appointmentId, AppointmentStatus.CANCELED.name)
    }

    override suspend fun getAllAppointments(): Result<List<Appointment>> {
        return try {
            val appointments = firestoreDatasource.getCollection<AppointmentDto>(
                collection = COLLECTION_APPOINTMENTS
            ).map { it.toDomain() }
            
            Result.Success(appointments)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
