package com.example.immunify.data.mapper

import com.example.immunify.data.firebase.dto.AppointmentDto
import com.example.immunify.data.model.AppointmentData
import com.example.immunify.data.model.AppointmentStatus
import com.example.immunify.domain.model.Appointment

/**
 * Mapper untuk convert antara AppointmentData (UI) -> Appointment (Domain) -> AppointmentDto (Firestore)
 */
object AppointmentMapper {

    /**
     * Convert dari AppointmentData (UI model) ke Domain model
     */
    fun AppointmentData.toDomain(): Appointment {
        return Appointment(
            id = this.id,
            userId = this.parent.id ?: "id1",
            userName = this.parent.name,
            userPhone = this.parent.phoneNumber ?: "12345678",
            clinicId = this.clinic.id,
            clinicName = this.clinic.name,
            clinicAddress = "${this.clinic.address}, ${this.clinic.district}, ${this.clinic.city}",
            date = this.date,
            timeSlot = this.timeSlot,
            vaccineId = this.vaccine.id,
            vaccineName = this.vaccine.name,
            vaccinantIds = this.vaccinants.map { it.id },
            vaccinantNames = this.vaccinants.map { it.name },
            status = this.status,
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )
    }
    
    /**
     * Convert list of AppointmentData to list of Appointment (Domain)
     */
    fun List<AppointmentData>.toDomainList(): List<Appointment> {
        return this.map { it.toDomain() }
    }

    /**
     * Convert dari Domain model ke Firestore DTO
     */
    fun Appointment.toDto(): AppointmentDto {
        return AppointmentDto(
            id = this.id,
            userId = this.userId,
            userName = this.userName,
            userPhone = this.userPhone,
            clinicId = this.clinicId,
            clinicName = this.clinicName,
            clinicAddress = this.clinicAddress,
            date = this.date,
            timeSlot = this.timeSlot,
            vaccineId = this.vaccineId,
            vaccineName = this.vaccineName,
            vaccinantIds = this.vaccinantIds,
            vaccinantNames = this.vaccinantNames,
            status = this.status.name,
            lotNumber = this.lotNumber,
            dose = this.dose,
            administrator = this.administrator,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )
    }

    /**
     * Convert dari Firestore DTO ke Domain model
     */
    fun AppointmentDto.toDomain(): Appointment {
        return Appointment(
            id = this.id,
            userId = this.userId,
            userName = this.userName,
            userPhone = this.userPhone,
            clinicId = this.clinicId,
            clinicName = this.clinicName,
            clinicAddress = this.clinicAddress,
            date = this.date,
            timeSlot = this.timeSlot,
            vaccineId = this.vaccineId,
            vaccineName = this.vaccineName,
            vaccinantIds = this.vaccinantIds,
            vaccinantNames = this.vaccinantNames,
            status = try {
                AppointmentStatus.valueOf(this.status)
            } catch (e: Exception) {
                AppointmentStatus.PENDING
            },
            lotNumber = this.lotNumber,
            dose = this.dose,
            administrator = this.administrator,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )
    }
}
