package com.example.immunify.data.mapper

import com.example.immunify.data.firebase.dto.VaccinationRecordDto
import com.example.immunify.domain.model.VaccinationRecord

/**
 * Mapper untuk convert antara VaccinationRecordDto dan VaccinationRecord
 */
object VaccinationRecordMapper {
    
    fun VaccinationRecordDto.toDomain(): VaccinationRecord {
        return VaccinationRecord(
            id = this.id,
            userId = this.userId,
            childId = this.childId,
            childName = this.childName,
            vaccineId = this.vaccineId,
            vaccineName = this.vaccineName,
            lotNumber = this.lotNumber,
            dose = this.dose,
            administrator = this.administrator,
            vaccinationDate = this.vaccinationDate,
            notes = this.notes,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )
    }
    
    fun VaccinationRecord.toDto(): VaccinationRecordDto {
        return VaccinationRecordDto(
            id = this.id,
            userId = this.userId,
            childId = this.childId,
            childName = this.childName,
            vaccineId = this.vaccineId,
            vaccineName = this.vaccineName,
            lotNumber = this.lotNumber,
            dose = this.dose,
            administrator = this.administrator,
            vaccinationDate = this.vaccinationDate,
            notes = this.notes,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )
    }
}
