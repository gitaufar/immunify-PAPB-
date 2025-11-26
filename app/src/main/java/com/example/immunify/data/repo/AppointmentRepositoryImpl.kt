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
 * 
 * Struktur Firestore:
 * users/
 *   {userId}/
 *     appointments/ (subcollection)
 *       {appointmentId}/
 */
class AppointmentRepositoryImpl @Inject constructor(
    private val firestoreDatasource: FirebaseFirestoreDatasource
) : AppointmentRepository {

    companion object {
        private const val COLLECTION_USERS = "users"
        private const val SUBCOLLECTION_APPOINTMENTS = "appointments"
    }

    override suspend fun createAppointment(appointment: Appointment): Result<String> {
        return try {
            val appointmentDto = appointment.toDto()
            
            // Generate ID jika belum ada
            val docId = appointmentDto.id.ifEmpty {
                firestoreDatasource.firestore
                    .collection(COLLECTION_USERS)
                    .document(appointment.userId)
                    .collection(SUBCOLLECTION_APPOINTMENTS)
                    .document().id
            }

            val finalDto = appointmentDto.copy(id = docId)
            
            // Save ke: users/{userId}/appointments/{appointmentId}
            firestoreDatasource.firestore
                .collection(COLLECTION_USERS)
                .document(appointment.userId)
                .collection(SUBCOLLECTION_APPOINTMENTS)
                .document(docId)
                .set(finalDto)
                .await()
            
            Result.Success(docId)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getAppointmentById(appointmentId: String): Result<Appointment> {
        return try {
            // Note: Untuk get by ID, perlu userId juga. 
            // Implementasi ini mungkin perlu direvisi jika butuh get tanpa userId
            Result.Error(Exception("Use getAppointmentsByUserId instead"))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getAppointmentsByUserId(userId: String): Result<List<Appointment>> {
        return try {
            // Query dari: users/{userId}/appointments
            val snapshot = firestoreDatasource.firestore
                .collection(COLLECTION_USERS)
                .document(userId)
                .collection(SUBCOLLECTION_APPOINTMENTS)
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
            // Note: Untuk update, perlu userId juga
            // Implementasi ini mungkin perlu parameter tambahan
            Result.Error(Exception("Use cancelAppointmentByUser instead"))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun cancelAppointment(appointmentId: String): Result<Unit> {
        return updateAppointmentStatus(appointmentId, AppointmentStatus.CANCELED.name)
    }
    
    /**
     * Cancel appointment dengan userId
     */
    suspend fun cancelAppointmentByUser(userId: String, appointmentId: String): Result<Unit> {
        return try {
            firestoreDatasource.firestore
                .collection(COLLECTION_USERS)
                .document(userId)
                .collection(SUBCOLLECTION_APPOINTMENTS)
                .document(appointmentId)
                .update(
                    mapOf(
                        "status" to AppointmentStatus.CANCELED.name,
                        "updatedAt" to System.currentTimeMillis()
                    )
                )
                .await()
            
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    /**
     * Update appointment status dengan userId
     */
    suspend fun updateAppointmentStatusByUser(
        userId: String, 
        appointmentId: String, 
        status: AppointmentStatus,
        lotNumber: String = "",
        dose: String = "",
        administrator: String = ""
    ): Result<Unit> {
        return try {
            val updates = mutableMapOf<String, Any>(
                "status" to status.name,
                "updatedAt" to System.currentTimeMillis()
            )
            
            // Add vaccination details if status is COMPLETED
            if (status == AppointmentStatus.COMPLETED) {
                if (lotNumber.isNotEmpty()) updates["lotNumber"] = lotNumber
                if (dose.isNotEmpty()) updates["dose"] = dose
                if (administrator.isNotEmpty()) updates["administrator"] = administrator
            }
            
            firestoreDatasource.firestore
                .collection(COLLECTION_USERS)
                .document(userId)
                .collection(SUBCOLLECTION_APPOINTMENTS)
                .document(appointmentId)
                .update(updates)
                .await()
            
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getAllAppointments(): Result<List<Appointment>> {
        return try {
            // Get all appointments dari semua user (admin feature)
            val allAppointments = mutableListOf<Appointment>()
            
            val usersSnapshot = firestoreDatasource.firestore
                .collection(COLLECTION_USERS)
                .get()
                .await()
            
            usersSnapshot.documents.forEach { userDoc ->
                val appointmentsSnapshot = userDoc.reference
                    .collection(SUBCOLLECTION_APPOINTMENTS)
                    .get()
                    .await()
                
                val userAppointments = appointmentsSnapshot.toObjects(AppointmentDto::class.java)
                    .map { it.toDomain() }
                
                allAppointments.addAll(userAppointments)
            }
            
            Result.Success(allAppointments)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
