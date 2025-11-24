package com.example.immunify.domain.usecase

import com.example.immunify.domain.model.Child
import com.example.immunify.domain.repo.ChildRepository
import com.example.immunify.util.Result
import javax.inject.Inject

/**
 * Use Case untuk create child profile
 */
class CreateChildUseCase @Inject constructor(
    private val childRepository: ChildRepository
) {
    suspend operator fun invoke(child: Child): Result<String> {
        // Validasi
        if (child.userId.isEmpty()) {
            return Result.Error(Exception("User ID is required"))
        }
        
        if (child.name.isEmpty()) {
            return Result.Error(Exception("Child name is required"))
        }
        
        if (child.birthDate.isEmpty()) {
            return Result.Error(Exception("Birth date is required"))
        }
        
        if (child.gender.isEmpty()) {
            return Result.Error(Exception("Gender is required"))
        }
        
        return childRepository.createChild(child)
    }
}
