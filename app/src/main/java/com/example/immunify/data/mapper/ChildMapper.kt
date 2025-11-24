package com.example.immunify.data.mapper

import com.example.immunify.data.firebase.dto.ChildDto
import com.example.immunify.data.model.ChildData
import com.example.immunify.domain.model.Child

/**
 * Mapper untuk Child
 */
object ChildMapper {
    
    /**
     * Convert dari ChildData (UI) ke Child (Domain)
     */
    fun ChildData.toDomain(userId: String): Child {
        return Child(
            id = this.id,
            userId = userId,
            name = this.name,
            birthDate = this.birthDate,
            gender = this.gender.name,
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )
    }
    
    /**
     * Convert dari Child (Domain) ke ChildDto (Firestore)
     */
    fun Child.toDto(): ChildDto {
        return ChildDto(
            id = this.id,
            userId = this.userId,
            name = this.name,
            birthDate = this.birthDate,
            gender = this.gender,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )
    }
    
    /**
     * Convert dari ChildDto (Firestore) ke Child (Domain)
     */
    fun ChildDto.toDomain(): Child {
        return Child(
            id = this.id,
            userId = this.userId,
            name = this.name,
            birthDate = this.birthDate,
            gender = this.gender,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )
    }
}
