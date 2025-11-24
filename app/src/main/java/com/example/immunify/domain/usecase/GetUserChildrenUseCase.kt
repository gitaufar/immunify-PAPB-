package com.example.immunify.domain.usecase

import com.example.immunify.domain.model.Child
import com.example.immunify.domain.repo.ChildRepository
import com.example.immunify.util.Result
import javax.inject.Inject

/**
 * Use Case untuk get children by user
 */
class GetUserChildrenUseCase @Inject constructor(
    private val childRepository: ChildRepository
) {
    suspend operator fun invoke(userId: String): Result<List<Child>> {
        if (userId.isEmpty()) {
            return Result.Error(Exception("User ID is required"))
        }
        
        return childRepository.getChildrenByUserId(userId)
    }
}
