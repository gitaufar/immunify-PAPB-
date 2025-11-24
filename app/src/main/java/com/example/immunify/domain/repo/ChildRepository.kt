package com.example.immunify.domain.repo

import com.example.immunify.domain.model.Child
import com.example.immunify.util.Result

/**
 * Repository interface untuk Child
 */
interface ChildRepository {
    
    suspend fun createChild(child: Child): Result<String>
    
    suspend fun getChildById(userId: String, childId: String): Result<Child>
    
    suspend fun getChildrenByUserId(userId: String): Result<List<Child>>
    
    suspend fun updateChild(child: Child): Result<Unit>
    
    suspend fun deleteChild(userId: String, childId: String): Result<Unit>
}
